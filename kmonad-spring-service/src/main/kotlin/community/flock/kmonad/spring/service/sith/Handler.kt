package community.flock.kmonad.spring.service.sith

import community.flock.kmonad.core.AppException
import community.flock.kmonad.core.sith.Context
import community.flock.kmonad.core.sith.deleteByUUID
import community.flock.kmonad.core.sith.getAll
import community.flock.kmonad.core.sith.getByUUID
import community.flock.kmonad.core.sith.save
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
import java.util.UUID
import community.flock.kmonad.spring.api.data.Sith as PotentialSith

@Indexed
@ResponseBody
@RequestMapping("/sith")
class Handler(private val context: Context) : SithApi {

    @GetMapping
    override fun getSith() = handle { getAll().toList() }.produce()

    @GetMapping("{uuid}")
    override fun getSithByUUID(@PathVariable uuid: String) = validate { UUID.fromString(uuid) }
        .handle { getByUUID(it) }.produce()


    @PostMapping
    override fun postSith(@RequestBody sith: PotentialSith) = handle { save(sith.consume()) }.produce()

    @DeleteMapping("{uuid}")
    override fun deleteSithByUUID(@PathVariable uuid: String) = validate { UUID.fromString(uuid) }
        .handle { deleteByUUID(it) }.produce()


    private fun <R, A> R.handle(block: suspend Context.(R) -> A) = let { runBlocking { context.block(it) } }

    private fun <A> validate(block: () -> A) = try {
        block()
    } catch (e: Exception) {
        throw AppException.BadRequest()
    }

}
