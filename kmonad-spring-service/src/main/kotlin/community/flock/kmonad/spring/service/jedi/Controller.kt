package community.flock.kmonad.spring.service.jedi

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.common.IO
import community.flock.kmonad.core.common.Reader
import community.flock.kmonad.core.jedi.Context
import community.flock.kmonad.core.jedi.bindDelete
import community.flock.kmonad.core.jedi.bindGet
import community.flock.kmonad.core.jedi.bindPost
import community.flock.kmonad.spring.api.JediApi
import community.flock.kmonad.spring.service.jedi.data.consume
import community.flock.kmonad.spring.service.jedi.data.produce
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
import community.flock.kmonad.spring.api.data.Jedi as PotentialJedi

@Indexed
@ResponseBody
@RequestMapping("/jedi")
class Controller(private val context: Context) : JediApi {

    @GetMapping
    override fun getJedi() = handle { bindGet() }.let { runBlocking { it.toList() } }.produce()

    @GetMapping("{uuid}")
    override fun getJediByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }.produce()

    @PostMapping
    override fun postJedi(@RequestBody jedi: PotentialJedi) = handle { bindPost(jedi.consume()) }.produce()

    @DeleteMapping("{uuid}")
    override fun deleteJediByUUID(@PathVariable uuid: String) = handle { bindDelete(uuid) }.produce()


    private fun <A> handle(block: () -> Reader<Context, IO<Either<AppException, A>>>) =
        block().provide(context).runUnsafe().getOrHandle { throw it }

}
