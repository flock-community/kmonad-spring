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
    fun getJedi() = handle { bindGet() }

    @GetMapping("{uuid}")
    fun getJediByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }

    @PostMapping
    fun postJedi(@RequestBody jedi: Jedi) = handle { bindPost(jedi) }

    @DeleteMapping("{uuid}")
    fun deleteJedi(@PathVariable uuid: String) = handle { bindDelete(uuid) }


    private fun <A> handle(block: () -> Reader<Context, IO<Either<AppException, A>>>) =
        block().provide(context).runUnsafe().getOrHandle { throw it }

}
