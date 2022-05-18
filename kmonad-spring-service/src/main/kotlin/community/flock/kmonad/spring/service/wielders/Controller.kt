package community.flock.kmonad.spring.service.wielders

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.forcewielder.ForceWielderContext
import community.flock.kmonad.core.forcewielder.bindGet
import community.flock.kmonad.spring.api.ForceWielderApi
import community.flock.kmonad.spring.service.wielders.data.Producer.forWielder
import community.flock.kmonad.spring.service.wielders.data.Producer.forWielders
import kotlinx.coroutines.ExperimentalCoroutinesApi
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
class Controller(private val context: ForceWielderContext) : ForceWielderApi {

    @GetMapping
    override fun getWielders() = forWielders().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getWielderByUUID(@PathVariable uuid: String) = forWielder().handle { bindGet(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend ForceWielderContext.() -> T) = runBlocking {
        context.block().produce()
    }

}
