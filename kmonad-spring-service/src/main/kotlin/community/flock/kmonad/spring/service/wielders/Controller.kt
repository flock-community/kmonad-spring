package community.flock.kmonad.spring.service.wielders

import community.flock.kmonad.core.common.Producible
import community.flock.kmonad.core.wielders.Context
import community.flock.kmonad.core.wielders.bindGet
import community.flock.kmonad.spring.api.ForceWielderApi
import community.flock.kmonad.spring.service.wielders.data.Producer.forWielder
import community.flock.kmonad.spring.service.wielders.data.Producer.forWielderFlow
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
class Controller(private val context: Context) : ForceWielderApi {

    @GetMapping
    override fun getWielders() = forWielderFlow().handle { bindGet() }

    @GetMapping("{uuid}")
    override fun getWielderByUUID(@PathVariable uuid: String) = forWielder().handle { bindGet(uuid) }


    private fun <T, R> Producible<T, R>.handle(block: suspend Context.() -> T) = runBlocking {
        context.block().produce()
    }

}
