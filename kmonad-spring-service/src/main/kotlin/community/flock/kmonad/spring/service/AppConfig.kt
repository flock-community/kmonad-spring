package community.flock.kmonad.spring.service

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.spring.service.common.LiveLayer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import community.flock.kmonad.core.droids.Context as DroidContext
import community.flock.kmonad.core.droids.Repository as DroidRepository
import community.flock.kmonad.core.jedi.Context as JediContext
import community.flock.kmonad.core.jedi.Repository as JediRepository
import community.flock.kmonad.core.sith.Context as SithContext
import community.flock.kmonad.core.sith.Repository as SithRepository
import community.flock.kmonad.core.wielders.Context as WielderContext
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
    fun jediController(): JediController = JediController(object : JediContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    fun sithController(): SithController = SithController(object : SithContext {
        override val sithRepository: SithRepository = LiveSithRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    @ExperimentalCoroutinesApi
    fun wielderController(): WielderController = WielderController(object : WielderContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val sithRepository: SithRepository = LiveSithRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    fun droidController(): DroidController = DroidController(object : DroidContext {
        override val droidRepository: DroidRepository = LiveDroidRepository(liveLayer)
    })

}
