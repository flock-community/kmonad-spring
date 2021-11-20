package community.flock.kmonad.spring.service.droids

import arrow.core.Either
import arrow.core.getOrHandle
import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.droids.pipe.Context
import community.flock.kmonad.core.droids.pipe.bindDelete
import community.flock.kmonad.core.droids.pipe.bindGet
import community.flock.kmonad.core.droids.pipe.bindPost
import community.flock.kmonad.spring.api.DroidApi
import community.flock.kmonad.spring.service.droids.data.consume
import community.flock.kmonad.spring.service.droids.data.produce
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
import community.flock.kmonad.spring.api.data.Droid as PotentialDroid

@Indexed
@ResponseBody
@RequestMapping("/droids")
class Handler(private val context: Context) : DroidApi {

    @GetMapping
    override fun getDroids() = handle { bindGet().map { it.toList() } }.produce()

    @GetMapping("{uuid}")
    override fun getDroidByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }.produce()

    @PostMapping
    override fun postDroid(@RequestBody droid: PotentialDroid) = handle { bindPost(droid.consume()) }.produce()

    @DeleteMapping("{uuid}")
    override fun deleteDroidByUUID(@PathVariable uuid: String) = handle { bindDelete(uuid) }.produce()


    private fun <A> handle(block: suspend Context.() -> Either<AppException, A>) =
        runBlocking { context.block().getOrHandle { throw it } }

}
