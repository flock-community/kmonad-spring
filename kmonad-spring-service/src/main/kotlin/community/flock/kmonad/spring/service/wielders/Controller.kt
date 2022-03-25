package community.flock.kmonad.spring.service.wielders

import community.flock.kmonad.core.wielders.Context
import community.flock.kmonad.core.wielders.bindGet
import community.flock.kmonad.spring.api.ForceWielderApi
import community.flock.kmonad.spring.service.wielders.data.produce
import kotlinx.coroutines.ExperimentalCoroutinesApi
import kotlinx.coroutines.flow.toList
import kotlinx.coroutines.runBlocking
import org.springframework.stereotype.Indexed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Indexed
@ResponseBody
@ExperimentalCoroutinesApi
@RequestMapping("/force-wielders")
class Controller(private val context: Context) : ForceWielderApi {

    @GetMapping
    override fun getWielders() = handle { bindGet().toList() }.produce()

    @GetMapping("{uuid}")
    override fun getWielderByUUID(@PathVariable uuid: String) = handle { bindGet(uuid) }.produce()


    private fun <A> handle(block: suspend Context.() -> A) = runBlocking { context.block() }

}
