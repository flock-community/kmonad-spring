package community.flock.kmonad.spring.service.jedi

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.AppException.InternalServerError
import community.flock.kmonad.core.AppException.NotFound
import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.Either.Companion.left
import community.flock.kmonad.core.common.monads.Either.Companion.right
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Option
import community.flock.kmonad.core.common.monads.flatMap
import community.flock.kmonad.core.common.monads.toOption
import community.flock.kmonad.core.jedi.Repository
import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.spring.service.common.DB.StarWars
import community.flock.kmonad.spring.service.common.HasLive
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.runBlocking
import org.litote.kmongo.eq
import java.util.UUID

interface LiveContext : HasLive.DatabaseClient

class LiveRepository(ctx: LiveContext) : Repository {

    private val collection = ctx.databaseClient.getDatabase(StarWars.name).getCollection<Jedi>()


    override fun getAll(): IO<Either<InternalServerError, Flow<Jedi>>> = IO { getAllAsEither() }

    override fun getByUUID(uuid: UUID): IO<Either<AppException, Option<Jedi>>> = IO { getByUUIDAsEither(uuid) }

    override fun save(jedi: Jedi): IO<Either<AppException, Jedi>> = IO { saveAsEither(jedi) }

    override fun deleteByUUID(uuid: UUID): IO<Either<AppException, Jedi>> = IO { deleteByUUIDAsEither(uuid) }


    private fun getAllAsEither() = guard { collection.find().toFlow() }

    private fun getByUUIDAsEither(uuid: UUID) = guard { collection.findOne(Jedi::id eq uuid.toString()) }
        .flatMap { it.toOption().right() }

    private fun saveAsEither(jedi: Jedi) = UUID.fromString(jedi.id).let { uuid ->
        getByUUIDAsEither(uuid)
            .flatMap { it.fold({ guard { collection.insertOne(jedi) } }, { AppException.Conflict(uuid).left() }) }
            .flatMap {
                if (it.wasAcknowledged()) jedi.right()
                else InternalServerError().left()
            }
    }

    private fun deleteByUUIDAsEither(uuid: UUID) = getByUUIDAsEither(uuid)
        .flatMap { option -> option.fold({ NotFound(uuid).left() }, { it.right() }) }
        .flatMap { jedi ->
            guard { collection.deleteOne(Jedi::id eq jedi.id) }
                .flatMap {
                    if (it.wasAcknowledged()) jedi.right()
                    else InternalServerError().left()
                }
        }

}


private fun <A> guard(block: suspend () -> A) = guardWith(AppException::InternalServerError, block)


private fun <E : AppException, A> guardWith(errorBlock: (ex: Exception) -> E, block: suspend () -> A) = runBlocking {
    try {
        block().right()
    } catch (ex: Exception) {
        errorBlock(ex).left()
    }
}
