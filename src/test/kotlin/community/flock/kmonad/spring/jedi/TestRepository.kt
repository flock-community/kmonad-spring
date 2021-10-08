package community.flock.kmonad.spring.jedi

import arrow.core.Either
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.jedi.pipe.Repository
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.flow.flowOf
import java.util.UUID

object TestRepository : Repository {

    override fun getAll(): IO<Either<AppException, Flow<Jedi>>> =
        IO { Either.Right(flowOf(Jedi(name = "Luke", age = 20), Jedi(name = "Yoda", age = 942))) }

    override fun getByUUID(uuid: UUID): IO<Either<AppException, Jedi>> =
        getAll().map { it.map { flow -> flow.first() } }

    override fun save(jedi: Jedi): IO<Either<AppException, Jedi>> = IO { Either.Right(jedi) }

    override fun deleteByUUID(uuid: UUID) = getByUUID(uuid)

}
