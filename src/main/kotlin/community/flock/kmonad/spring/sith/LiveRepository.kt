package community.flock.kmonad.spring.sith

import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.define.Has
import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.core.sith.pipe.Repository
import community.flock.kmonad.spring.common.DB.StarWars
import community.flock.kmonad.spring.common.HasLive
import org.litote.kmongo.eq
import java.util.UUID

interface LiveContext : HasLive.DatabaseClient, Has.Logger

class LiveRepository(ctx: LiveContext) : Repository {

    private val collection = ctx.databaseClient.getDatabase(StarWars.name).getCollection<Sith>()

    override suspend fun getAll() = guard { collection.find().toFlow() }

    override suspend fun getByUUID(uuid: UUID): Sith = guard { collection.findOne(Sith::id eq uuid.toString()) }
        ?: throw NotFound(uuid)

    override suspend fun save(sith: Sith): Sith {
        val uuid = UUID.fromString(sith.id)
        val exception = runCatching { getByUUID(uuid) }.exceptionOrNull() ?: throw Conflict(uuid)
        val result = if (exception is NotFound) guard { collection.insertOne(sith) } else throw exception
        return if (result.wasAcknowledged()) sith else throw InternalServerError()
    }

    override suspend fun deleteByUUID(uuid: UUID): Sith {
        val sith = getByUUID(uuid)
        val result = guard { collection.deleteOne(Sith::id eq uuid.toString()) }
        return if (result.wasAcknowledged()) sith else throw InternalServerError()
    }

}

private inline fun <A> guard(block: () -> A) = try {
    block()
} catch (e: DuplicateKeyException) {
    throw Conflict(null, e.cause)
} catch (e: MongoException) {
    throw InternalServerError(e.cause)
} catch (e: Exception) {
    throw InternalServerError(e.cause)
}
