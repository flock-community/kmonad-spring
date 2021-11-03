package community.flock.kmonad.spring.droids

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.data.Droid
import community.flock.kmonad.core.droids.pipe.Context
import community.flock.kmonad.core.droids.pipe.bindDelete
import community.flock.kmonad.core.droids.pipe.bindGet
import community.flock.kmonad.core.droids.pipe.bindPost
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
@RequestMapping("/droids")
class Handler(private val context: Context) {

    @GetMapping
    fun getDroids() = handle { context.bindGet() }

    @GetMapping("{uuid}")
    fun getDroidByUUID(@PathVariable uuid: String) = handle { context.bindGet(uuid) }

    @PostMapping
    fun postDroid(@RequestBody droid: Droid) = handle { context.bindPost(droid) }

    @DeleteMapping("{uuid}")
    fun deleteJedi(@PathVariable uuid: String) = handle { context.bindDelete(uuid) }


    private fun <A> handle(block: suspend () -> Either<AppException, A>) =
        runBlocking { block().getOrHandle { throw it } }

}
