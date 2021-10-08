package community.flock.kmonad.spring.sith

import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.define.DB
import community.flock.kmonad.core.common.define.HasLogger
import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.core.sith.pipe.Repository
import community.flock.kmonad.spring.common.HasDatabaseClient
import org.litote.kmongo.eq
import java.util.UUID

interface LiveRepositoryContext : HasDatabaseClient, HasLogger

class LiveRepository(ctx: LiveRepositoryContext) : Repository {

    private val collection = ctx.databaseClient.getDatabase(DB.StarWars.name).getCollection<Sith>()

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

private suspend fun <R> guard(block: suspend () -> R) = try {
    block()
} catch (e: DuplicateKeyException) {
    throw Conflict(null, e.cause)
} catch (e: MongoException) {
    throw InternalServerError(e.cause)
} catch (e: Exception) {
    throw InternalServerError(e.cause)
}
