package community.flock.kmonad.spring.service.droids

import arrow.core.continuations.Effect
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.droid.DroidContext
import community.flock.kmonad.core.droid.bindDelete
import community.flock.kmonad.core.droid.bindGet
import community.flock.kmonad.core.droid.bindPost
import community.flock.kmonad.spring.api.DroidApi
import community.flock.kmonad.spring.service.droids.data.Producer.forDroid
import community.flock.kmonad.spring.service.droids.data.Producer.forDroidFlow
import community.flock.kmonad.spring.service.droids.data.consume
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Indexed
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import community.flock.kmonad.spring.api.data.Droid as PotentialDroid

@Indexed
@ResponseBody
@RequestMapping("/droids")
class Controller(private val context: DroidContext) : DroidApi {

    @GetMapping
    override fun getDroids() = forDroidFlow().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getDroidByUUID(@PathVariable uuid: String) = forDroid().handle { bindGet(uuid) }

    @PostMapping
    override fun postDroid(@RequestBody droid: PotentialDroid) = forDroid().handle { bindPost(droid.consume()) }

    @DeleteMapping("{uuid}")
    override fun deleteDroidByUUID(@PathVariable uuid: String) = forDroid().handle { bindDelete(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend DroidContext.() -> Effect<AppException, T>) =
        runBlocking { context.block().toEither().map { it.produce() } }
            .getOrHandle { throw it }

}
