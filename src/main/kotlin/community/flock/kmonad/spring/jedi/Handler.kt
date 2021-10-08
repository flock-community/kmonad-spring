package community.flock.kmonad.spring.jedi

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.core.jedi.pipe.Context
import community.flock.kmonad.core.jedi.pipe.bindDelete
import community.flock.kmonad.core.jedi.pipe.bindGet
import community.flock.kmonad.core.jedi.pipe.bindPost
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
@RequestMapping("/jedi")
class Handler(private val context: Context) {

    @GetMapping
    suspend fun getJedi() = bindGet().go()

    @GetMapping("{uuid}")
    suspend fun getJediByUUID(@PathVariable uuid: String) = bindGet(uuid).go()

    @PostMapping
    suspend fun postJedi(@RequestBody jedi: Jedi) = bindPost(jedi).go()

    @DeleteMapping("{uuid}")
    suspend fun deleteJedi(@PathVariable uuid: String) = bindDelete(uuid).go()

    private suspend fun <R> Reader<Context, IO<Either<AppException, R>>>.go() =
        provide(context).runUnsafe().getOrHandle { throw it }

}