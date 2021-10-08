package community.flock.kmonad.spring

import community.flock.kmonad.core.common.define.Logger
import community.flock.kmonad.spring.common.LiveLayer
import community.flock.kmonad.spring.common.LiveLogger
import org.springframework.context.annotation.Bean
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import community.flock.kmonad.core.jedi.pipe.Context as JediContext
import community.flock.kmonad.core.jedi.pipe.Repository as JediRepository
import community.flock.kmonad.spring.jedi.Controller as JediController
import community.flock.kmonad.spring.jedi.LiveRepository as LiveJediRepository

@Configuration
class AppConfig(env: Environment) {

    private val host = env.getRequiredProperty("db.host")

    @Bean
    fun jediController(): JediController = JediController(object : JediContext {
        override val jediRepository: JediRepository = LiveJediRepository(LiveLayer(host))
        override val logger: Logger = LiveLogger
    })

}
