package community.flock.kmonad.spring.jedi

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.core.jedi.pipe.Context
import community.flock.kmonad.core.jedi.pipe.Repository
import community.flock.kmonad.spring.common.DefaultTestLogger
import kotlinx.coroutines.flow.first
import kotlinx.coroutines.runBlocking
import org.junit.jupiter.api.Assertions.assertEquals
import org.junit.jupiter.api.Test

class JediTests {

    private val handler: Handler = Handler(object : Context {
        override val jediRepository: Repository = TestRepository
        override val logger: Logger = DefaultTestLogger
    })

    @Test
    fun testJedi() = runBlocking {
        val name = handler.getJedi().first().name
        assertEquals("Luke", name)
    }

}
