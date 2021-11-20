package community.flock.kmonad.spring.service

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.spring.service.common.LiveLayer
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import community.flock.kmonad.core.droids.pipe.Context as DroidContext
import community.flock.kmonad.core.droids.pipe.Repository as DroidRepository
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.core.sith.pipe.Repository as SithRepository
import community.flock.kmonad.core.wielders.pipe.Context as WielderContext
import community.flock.kmonad.spring.service.droids.Handler as DroidHandler
import community.flock.kmonad.spring.service.droids.LiveRepository as LiveDroidRepository
import community.flock.kmonad.spring.service.jedi.Handler as JediHandler
import community.flock.kmonad.spring.service.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.service.sith.Handler as SithHandler
import community.flock.kmonad.spring.service.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.service.wielders.Handler as WielderHandler

@Configuration
class AppConfig(private val liveLayer: LiveLayer) {

    @Bean
    fun jediHandler(): JediHandler = JediHandler(object : JediContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    fun sithHandler(): SithHandler = SithHandler(object : SithContext {
        override val sithRepository: SithRepository = LiveSithRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    @ExperimentalCoroutinesApi
    fun wielderHandler(): WielderHandler = WielderHandler(object : WielderContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val sithRepository: SithRepository = LiveSithRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

    @Bean
    fun droidHandler(): DroidHandler = DroidHandler(object : DroidContext {
        override val droidRepository: DroidRepository = LiveDroidRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

}
