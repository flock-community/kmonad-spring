package community.flock.kmonad.spring.wielders

import com.fasterxml.jackson.module.kotlin.jacksonObjectMapper
import community.flock.kmonad.core.wielders.pipe.Context
import community.flock.kmonad.core.wielders.pipe.bindGet
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
class Handler(private val context: Context) {

    @GetMapping
    fun getWielders() = runBlocking { context.bindGet().toList().map { jacksonObjectMapper().writeValueAsString(it) } }

    @GetMapping("{uuid}")
    fun getWielderByUUID(@PathVariable uuid: String) = runBlocking { context.bindGet(uuid) }

}
