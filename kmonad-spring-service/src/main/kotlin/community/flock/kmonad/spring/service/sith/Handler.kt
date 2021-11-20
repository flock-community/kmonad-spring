package community.flock.kmonad.spring.service.sith

import community.flock.kmonad.core.sith.pipe.Context
import community.flock.kmonad.core.sith.pipe.bindDelete
import community.flock.kmonad.core.sith.pipe.bindGet
import community.flock.kmonad.core.sith.pipe.bindPost
import community.flock.kmonad.spring.api.SithApi
import community.flock.kmonad.spring.service.sith.data.consume
import community.flock.kmonad.spring.service.sith.data.produce
import kotlinx.coroutines.flow.toList
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
class Handler(private val context: Context) : SithApi {

    @GetMapping
    override fun getSith() = handle { bindGet().toList() }.produce()

    @GetMapping("{uuid}")
    override fun getSithByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }.produce()

    @PostMapping
    override fun postSith(@RequestBody sith: PotentialSith) = handle { bindPost(sith.consume()) }.produce()

    @DeleteMapping("{uuid}")
    override fun deleteSithByUUID(@PathVariable uuid: String) = handle { bindDelete(uuid) }.produce()


    private fun <A> handle(block: suspend Context.() -> A) = runBlocking { context.block() }

}
