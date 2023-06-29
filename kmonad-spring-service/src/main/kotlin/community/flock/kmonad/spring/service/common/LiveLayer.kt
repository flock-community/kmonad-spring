package community.flock.kmonad.spring.service.common

import com.mongodb.ConnectionString
import community.flock.kmonad.core.common.AppLayer
import community.flock.kmonad.spring.service.droids.LiveDroidRepository
import community.flock.kmonad.spring.service.jedi.LiveJediRepository
import community.flock.kmonad.spring.service.sith.LiveSithRepository
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.springframework.core.env.Environment
import org.springframework.stereotype.Component

@Component
class LiveLayer(env: Environment) : AppLayer {
    private val dbHost = env.getRequiredProperty("spring.data.mongodb.host")
    private val dbPort = env.getProperty("spring.data.mongodb.port") ?: 27017

    private val dbClient = KMongo.createClient(ConnectionString("mongodb://$dbHost:$dbPort")).coroutine

    final override val logger = LiveLogger
    final override val droidRepository = LiveDroidRepository(dbClient)
    final override val jediRepository = LiveJediRepository(dbClient)
    final override val sithRepository = LiveSithRepository(dbClient, logger)
}
