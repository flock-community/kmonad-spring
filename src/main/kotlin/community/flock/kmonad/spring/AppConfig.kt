package community.flock.kmonad.spring

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.core.sith.pipe.Repository
import community.flock.kmonad.spring.common.LiveLayer
import community.flock.kmonad.spring.common.LiveLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.core.sith.pipe.Context as SithContext
import community.flock.kmonad.spring.jedi.Controller as JediController
import community.flock.kmonad.spring.jedi.LiveRepository as LiveJediRepository
import community.flock.kmonad.spring.sith.Controller as SithController
import community.flock.kmonad.spring.sith.LiveRepository as LiveSithRepository

@Configuration
class AppConfig(env: Environment) {

    private val host = env.getRequiredProperty("db.host")

    private val liveLayer = LiveLayer(host)

    @Bean
    fun jediController(): JediController = JediController(object : JediContext {
        override val jediRepository: JediRepository = LiveJediRepository(liveLayer)
        override val logger: Logger = LiveLogger
    })

    @Bean
    fun sithController(): SithController = SithController(object : SithContext {
        override val sithRepository: Repository = LiveSithRepository(liveLayer)
        override val logger: Logger = liveLayer.logger
    })

}
