package community.flock.kmonad.spring.service

import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import community.flock.kmonad.core.droid.DroidContext
import community.flock.kmonad.core.jedi.JediContext
import community.flock.kmonad.core.sith.SithContext
import community.flock.kmonad.core.forcewielder.ForceWielderContext
import community.flock.kmonad.spring.service.droids.Controller as DroidController
import community.flock.kmonad.spring.service.droids.LiveRepository as LiveDroidRepository
import community.flock.kmonad.spring.service.jedi.Controller as JediController
import community.flock.kmonad.spring.service.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.service.sith.Controller as SithController
import community.flock.kmonad.spring.service.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.service.wielders.Controller as WielderController

@Configuration
class AppConfig(private val liveLayer: LiveLayer) {

    @Bean
    fun jediController() = JediController(object : JediContext {
        override val jediRepository = LiveJediRepository(liveLayer)
        override val logger = liveLayer.logger
    })

    @Bean
    fun sithController() = SithController(object : SithContext {
        override val sithRepository = LiveSithRepository(liveLayer)
        override val logger = liveLayer.logger
    })

    @Bean
    @ExperimentalCoroutinesApi
    fun wielderController() = WielderController(object : ForceWielderContext {
        override val jediRepository = LiveJediRepository(liveLayer)
        override val sithRepository = LiveSithRepository(liveLayer)
        override val logger = liveLayer.logger
    })

    @Bean
    fun droidController() = DroidController(object : DroidContext {
        override val droidRepository = LiveDroidRepository(liveLayer)
        override val logger = liveLayer.logger
    })

}
