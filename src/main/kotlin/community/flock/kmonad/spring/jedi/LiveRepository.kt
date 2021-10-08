package community.flock.kmonad.spring.jedi

import arrow.core.Either
import arrow.core.computations.either
import arrow.core.left
import arrow.core.right
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.common.define.DB
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.jedi.pipe.Repository
import community.flock.kmonad.spring.common.HasDatabaseClient
import kotlinx.coroutines.flow.Flow
import org.litote.kmongo.coroutine.CoroutineClient
import org.litote.kmongo.eq
import java.util.UUID

interface LiveRepositoryContext : HasDatabaseClient

class LiveRepository(ctx: LiveRepositoryContext) : Repository {

    private val collection = ctx.databaseClient.getDatabase(DB.StarWars.name).getCollection<Jedi>()

    override fun getAll(): IO<Either<InternalServerError, Flow<Jedi>>> = IO { getAllAsEither() }

    private fun getAllAsEither(): Either<InternalServerError, Flow<Jedi>> =
        guard { collection.find().toFlow() }

    override fun getByUUID(uuid: UUID): IO<Either<AppException, Jedi>> = IO {
        getByUUIDAsEither(uuid)
    }

    private suspend fun getByUUIDAsEither(uuid: UUID): Either<AppException, Jedi> = either {
        val maybeJedi = guard { collection.findOne(Jedi::id eq uuid.toString()) }.bind()
        maybeJedi ?: AppException.NotFound(uuid).left().bind()
    }

    override fun save(jedi: Jedi): IO<Either<AppException, Jedi>> = IO {
        saveAsEither(jedi)
    }

    private suspend fun saveAsEither(jedi: Jedi): Either<AppException, Jedi> = either {
        val uuid = UUID.fromString(jedi.id)
        val existingJedi = getByUUIDAsEither(uuid)
        if (existingJedi.isRight()) AppException.Conflict(uuid).left().bind() else {
            val result = guard { collection.insertOne(jedi) }.bind()
            val maybeJedi = jedi.takeIf { result.wasAcknowledged() }
            maybeJedi ?: InternalServerError().left().bind()
        }
    }

    override fun deleteByUUID(uuid: UUID): IO<Either<AppException, Jedi>> = IO {
        deleteByUUIDAsEither(uuid)
    }

    private suspend fun deleteByUUIDAsEither(uuid: UUID): Either<AppException, Jedi> =
        either {
            val jedi = getByUUIDAsEither(uuid).bind()
            val result = guard { collection.deleteOne(Jedi::id eq uuid.toString()) }.bind()
            val maybeJedi = jedi.takeIf { result.wasAcknowledged() }
            maybeJedi ?: InternalServerError().left().bind()
        }

}

private inline fun <R> guard(block: () -> R) =
    guardWith(AppException::InternalServerError, block)

private inline fun <E : AppException, R> guardWith(errorBlock: (ex: Exception) -> E, block: () -> R) = try {
    block().right()
} catch (ex: Exception) {
    errorBlock(ex).left()
}
