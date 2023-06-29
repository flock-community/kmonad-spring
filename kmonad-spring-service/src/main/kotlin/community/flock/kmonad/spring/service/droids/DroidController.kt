package community.flock.kmonad.spring.service.droids

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.droid.DroidContext
import community.flock.kmonad.core.droid.DroidDependencies
import community.flock.kmonad.core.droid.bindDelete
import community.flock.kmonad.core.droid.bindGet
import community.flock.kmonad.core.droid.bindPost
import community.flock.kmonad.spring.api.DroidApi
import community.flock.kmonad.spring.service.droids.data.Producer.forDroid
import community.flock.kmonad.spring.service.droids.data.Producer.forDroidFlow
import community.flock.kmonad.spring.service.droids.data.consume
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import community.flock.kmonad.spring.api.data.Droid as PotentialDroid

@RestController
@RequestMapping("/droids")
class DroidController<R : DroidDependencies>(appLayer: R) : DroidApi {

    private val context = object : DroidContext {
        override val droidRepository = appLayer.droidRepository
        override val logger = appLayer.logger
    }

    @GetMapping
    override fun getDroids() = forDroidFlow().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getDroidByUUID(@PathVariable uuid: String) = forDroid().handle { bindGet(uuid) }

    @PostMapping
    override fun postDroid(@RequestBody droid: PotentialDroid) = forDroid().handle { bindPost(droid.consume()) }

    @DeleteMapping("{uuid}")
    override fun deleteDroidByUUID(@PathVariable uuid: String) = forDroid().handle { bindDelete(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend DroidContext.() -> Either<AppException, T>) =
        runBlocking { context.block().map { it.produce() } }
            .getOrHandle { throw it }

}
