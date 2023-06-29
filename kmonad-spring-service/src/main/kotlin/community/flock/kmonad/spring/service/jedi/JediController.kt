package community.flock.kmonad.spring.service.jedi

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.monads.Either
import community.flock.kmonad.core.common.monads.IO
import community.flock.kmonad.core.common.monads.Reader
import community.flock.kmonad.core.common.monads.getOrHandle
import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.jedi.JediContext
import community.flock.kmonad.core.jedi.JediDependencies
import community.flock.kmonad.core.jedi.bindDelete
import community.flock.kmonad.core.jedi.bindGet
import community.flock.kmonad.core.jedi.bindPost
import community.flock.kmonad.spring.api.JediApi
import community.flock.kmonad.spring.service.jedi.data.Producer.forMultipleJedi
import community.flock.kmonad.spring.service.jedi.data.Producer.forSingleJedi
import community.flock.kmonad.spring.service.jedi.data.consume
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import community.flock.kmonad.spring.api.data.Jedi as PotentialJedi

@RestController
@RequestMapping("/jedi")
class JediController<R : JediDependencies>(appLayer: R) : JediApi {

    private val context = object : JediContext {
        override val jediRepository = appLayer.jediRepository
        override val logger = appLayer.logger
    }

    @GetMapping
    override fun getJedi() = forMultipleJedi().handle { bindGet() }.let { runBlocking { it } }

    @GetMapping("{uuid}")
    override fun getJediByUUID(@PathVariable uuid: String) = forSingleJedi().handle { bindGet(uuid) }

    @PostMapping
    override fun postJedi(@RequestBody jedi: PotentialJedi) = forSingleJedi().handle { bindPost(jedi.consume()) }

    @DeleteMapping("{uuid}")
    override fun deleteJediByUUID(@PathVariable uuid: String) = forSingleJedi().handle { bindDelete(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: () -> Reader<JediContext, IO<Either<AppException, T>>>) =
        block().provide(context).runUnsafe().getOrHandle { throw it }
            .let { runBlocking { it.produce() } }

}
