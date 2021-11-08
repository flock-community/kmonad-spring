package community.flock.kmonad.spring.sith

import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.core.sith.pipe.Context
import community.flock.kmonad.core.sith.pipe.bindDelete
import community.flock.kmonad.core.sith.pipe.bindGet
import community.flock.kmonad.core.sith.pipe.bindPost
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Indexed
import org.springframework.web.bind.annotation.DeleteMapping
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Indexed
@ResponseBody
@RequestMapping("/sith")
class Handler(private val context: Context) {

    @GetMapping
    fun getSith() = handle { bindGet() }

    @GetMapping("{uuid}")
    fun getSithByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }

    @PostMapping
    fun postSith(@RequestBody sith: Sith) = handle { bindPost(sith) }

    @DeleteMapping("{uuid}")
    fun deleteSith(@PathVariable uuid: String) = handle { bindDelete(uuid) }


    private fun <A> handle(block: suspend Context.() -> A) = runBlocking { context.block() }

}
