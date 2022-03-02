package community.flock.kmonad.spring.service

import community.flock.kmonad.spring.service.common.IntegrationTestLayer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import community.flock.kmonad.core.jedi.Context as JediContext
import community.flock.kmonad.core.sith.Context as SithContext
import community.flock.kmonad.core.wielders.Context as WieldersContext
import community.flock.kmonad.spring.service.jedi.Handler as JediHandler
import community.flock.kmonad.spring.service.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.service.sith.Handler as SithHandler
import community.flock.kmonad.spring.service.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.service.wielders.Handler as WieldersHandler

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
