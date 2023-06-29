package community.flock.kmonad.spring.service.common

import com.mongodb.ConnectionString
import community.flock.kmonad.core.common.AppLayer
import community.flock.kmonad.core.common.Logger
import community.flock.kmonad.spring.service.droids.LiveDroidRepository
import community.flock.kmonad.spring.service.jedi.LiveJediRepository
import community.flock.kmonad.spring.service.sith.LiveSithRepository
import org.junit.jupiter.api.Assertions.assertTrue
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.springframework.context.annotation.Primary
import org.springframework.stereotype.Component

@Primary
@Component
object IntegrationTestLayer : AppLayer {

    private val databaseClient = KMongo.createClient(ConnectionString("mongodb://localhost:12345")).coroutine

    override val logger = object : Logger {
        override fun error(string: String) = assertTrue(string.isNotBlank())
        override fun log(string: String) = assertTrue(string.isNotBlank())
        override fun warn(string: String) = assertTrue(string.isNotBlank())
    }

    override val droidRepository = LiveDroidRepository(databaseClient)
    override val jediRepository = LiveJediRepository(databaseClient)
    override val sithRepository = LiveSithRepository(databaseClient, logger)


}
