package community.flock.kmonad.spring.service.sith

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.sith.SithContext
import community.flock.kmonad.core.sith.SithDependencies
import community.flock.kmonad.core.sith.bindDelete
import community.flock.kmonad.core.sith.bindGet
import community.flock.kmonad.core.sith.bindPost
import community.flock.kmonad.spring.api.SithApi
import community.flock.kmonad.spring.service.sith.data.Producer.forMultipleSith
import community.flock.kmonad.spring.service.sith.data.Producer.forSingleSith
import community.flock.kmonad.spring.service.sith.data.consume
import kotlinx.coroutines.runBlocking
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import community.flock.kmonad.spring.api.data.Sith as PotentialSith

@RestController
@RequestMapping("/sith")
class SithController<R : SithDependencies>(appLayer: R) : SithApi {

    private val context = object : SithContext {
        override val logger = appLayer.logger
        override val sithRepository = appLayer.sithRepository
    }

    @GetMapping
    override fun getSith() = forMultipleSith().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getSithByUUID(@PathVariable uuid: String) = forSingleSith().handle { bindGet(uuid) }

    @PostMapping
    override fun postSith(@RequestBody sith: PotentialSith) = forSingleSith().handle { bindPost(sith.consume()) }

    @DeleteMapping("{uuid}")
    override fun deleteSithByUUID(@PathVariable uuid: String) = forSingleSith().handle { bindDelete(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend SithContext.() -> Result<T>) = runBlocking {
        context.block().getOrThrow().produce()
    }

}
