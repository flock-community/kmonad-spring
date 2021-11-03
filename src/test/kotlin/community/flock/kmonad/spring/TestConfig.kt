package community.flock.kmonad.spring

import community.flock.kmonad.spring.common.IntegrationTestLayer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.core.wielders.pipe.Context as WieldersContext
import community.flock.kmonad.spring.jedi.Handler as JediHandler
import community.flock.kmonad.spring.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.sith.Handler as SithHandler
import community.flock.kmonad.spring.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.wielders.Handler as WieldersHandler

@TestConfiguration
class TestConfig {

    @Bean
    fun jediHandler() = JediHandler(object : JediContext {
        override val jediRepository = LiveJediRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

    @Bean
    fun sithHandler() = SithHandler(object : SithContext {
        override val sithRepository = LiveSithRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

    @Bean
    @ExperimentalCoroutinesApi
    fun wielderHandler() = WieldersHandler(object : WieldersContext {
        override val jediRepository = LiveJediRepository(IntegrationTestLayer)
        override val sithRepository = LiveSithRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

}
