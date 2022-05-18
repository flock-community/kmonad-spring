package community.flock.kmonad.spring.service

import community.flock.kmonad.spring.service.common.IntegrationTestLayer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.boot.test.context.TestConfiguration
import org.springframework.context.annotation.Bean
import community.flock.kmonad.core.jedi.JediContext
import community.flock.kmonad.core.sith.SithContext
import community.flock.kmonad.core.forcewielder.ForceWielderContext
import community.flock.kmonad.spring.service.jedi.Controller as JediController
import community.flock.kmonad.spring.service.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.service.sith.Controller as SithController
import community.flock.kmonad.spring.service.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.service.wielders.Controller as WieldersController

@TestConfiguration
class TestConfig {

    @Bean
    fun jediController() = JediController(object : JediContext {
        override val jediRepository = LiveJediRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

    @Bean
    fun sithController() = SithController(object : SithContext {
        override val sithRepository = LiveSithRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

    @Bean
    @ExperimentalCoroutinesApi
    fun wielderController() = WieldersController(object : ForceWielderContext {
        override val jediRepository = LiveJediRepository(IntegrationTestLayer)
        override val sithRepository = LiveSithRepository(IntegrationTestLayer)
        override val logger = IntegrationTestLayer.logger
    })

}
