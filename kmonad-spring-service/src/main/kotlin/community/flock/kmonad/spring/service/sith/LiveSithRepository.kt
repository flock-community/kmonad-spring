package community.flock.kmonad.spring.service.sith

import com.mongodb.DuplicateKeyException
import com.mongodb.MongoException
import community.flock.kmonad.core.AppException.Conflict
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.core.sith.SithRepository
import community.flock.kmonad.core.sith.model.Sith
import community.flock.kmonad.spring.service.common.DB.StarWars
import java.util.UUID
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.eq


class LiveSithRepository(client: CoroutineClient, private val logger: Logger) : SithRepository {

    private val collection = client.getDatabase(StarWars.name).getCollection<Sith>()

    override suspend fun getAll() = runCatching {
        guard { collection.find().toList() }
    }

    override suspend fun getByUUID(uuid: UUID) = runCatching {
        guard { collection.findOne(Sith::id eq uuid.toString()) } ?: throw NotFound(uuid)
    }

    override suspend fun save(sith: Sith) = runCatching {
        val uuid = UUID.fromString(sith.id)
        val exception = getByUUID(uuid).also { logger.log(it.toString()) }.exceptionOrNull() ?: throw Conflict(uuid)
        val result = if (exception is NotFound) guard { collection.insertOne(sith) } else throw exception
        if (result.wasAcknowledged()) sith else throw InternalServerError()
    }

    override suspend fun deleteByUUID(uuid: UUID) = runCatching {
        val sith = getByUUID(uuid).getOrThrow()
        val result = guard { collection.deleteOne(Sith::id eq uuid.toString()) }
        if (result.wasAcknowledged()) sith else throw InternalServerError()
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
