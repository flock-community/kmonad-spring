package community.flock.kmonad.spring.sith

import community.flock.kmonad.core.sith.pipe.bindDelete
import community.flock.kmonad.core.sith.pipe.bindGet
import community.flock.kmonad.core.sith.pipe.bindPost
import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.core.sith.pipe.Context
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
    suspend fun getSith() = context.bindGet()

    @GetMapping("{uuid}")
    suspend fun getSithByUUID(@PathVariable uuid: String) = context.bindGet(uuid)

    @PostMapping
    suspend fun postSith(@RequestBody sith: Sith) = context.bindPost(sith)

    @DeleteMapping("{uuid}")
    suspend fun deleteSith(@PathVariable uuid: String) = context.bindDelete(uuid)

}
