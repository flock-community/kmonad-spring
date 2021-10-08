package community.flock.kmonad.spring.wielders

import community.flock.kmonad.core.wielders.pipe.Context
import community.flock.kmonad.core.wielders.pipe.bindGet
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.stereotype.Indexed
import org.springframework.web.bind.annotation.GetMapping
import org.springframework.web.bind.annotation.PathVariable
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.ResponseBody

@Indexed
@ResponseBody
@ExperimentalCoroutinesApi
@RequestMapping("/force-wielders")
class Handler(private val context: Context) {

    @GetMapping
    suspend fun getWielders() = context.bindGet()

    @GetMapping("{uuid}")
    suspend fun getWielderByUUID(@PathVariable uuid: String) = context.bindGet(uuid)

}
