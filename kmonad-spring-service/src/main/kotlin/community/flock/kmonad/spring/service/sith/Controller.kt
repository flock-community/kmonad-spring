package community.flock.kmonad.spring.service.sith

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.sith.Context
import community.flock.kmonad.core.sith.bindDelete
import community.flock.kmonad.core.sith.bindGet
import community.flock.kmonad.core.sith.bindPost
import community.flock.kmonad.spring.api.SithApi
import community.flock.kmonad.spring.service.sith.data.Producer.forSith
import community.flock.kmonad.spring.service.sith.data.Producer.forSithFlow
import community.flock.kmonad.spring.service.sith.data.consume
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Indexed
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody
import community.flock.kmonad.spring.api.data.Sith as PotentialSith

@Indexed
@ResponseBody
@RequestMapping("/sith")
class Controller(private val context: Context) : SithApi {

    @GetMapping
    override fun getSith() = forSithFlow().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getSithByUUID(@PathVariable uuid: String) = forSith().handle { bindGet(uuid) }

    @PostMapping
    override fun postSith(@RequestBody sith: PotentialSith) = forSith().handle { bindPost(sith.consume()) }

    @DeleteMapping("{uuid}")
    override fun deleteSithByUUID(@PathVariable uuid: String) = forSith().handle { bindDelete(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend Context.() -> T) = runBlocking {
        context.block().produce()
    }

}
