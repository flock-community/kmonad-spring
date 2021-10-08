package community.flock.kmonad.spring

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.spring.common.LiveLayer
import community.flock.kmonad.spring.common.LiveLogger
import kotlinx.coroutines.ExperimentalCoroutinesApi
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.core.sith.pipe.Repository as SithRepository
import community.flock.kmonad.core.wielders.pipe.Context as WielderContext
import community.flock.kmonad.spring.jedi.Handler as JediHandler
import community.flock.kmonad.spring.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.sith.Handler as SithHandler
import community.flock.kmonad.spring.sith.LiveRepository as LiveSithRepository
import community.flock.kmonad.spring.wielders.Handler as WielderHandler

@Configuration
class AppConfig(private val liveLayer: LiveLayer) {

    @Bean
    fun jediHandler(): JediHandler = JediHandler(object : JediContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val logger: Logger = LiveLogger
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

}
