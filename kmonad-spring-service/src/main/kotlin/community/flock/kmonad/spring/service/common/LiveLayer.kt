package community.flock.kmonad.spring.service.common

import com.mongodb.ConnectionString
import community.flock.kmonad.core.common.define.Logger
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import community.flock.kmonad.spring.service.droids.LiveContext as LiveDroidContext
import community.flock.kmonad.spring.service.jedi.LiveContext as LiveJediContext
import community.flock.kmonad.spring.service.sith.LiveContext as LiveSithContext

@Configuration
class LiveLayer(env: Environment) : LiveJediContext, LiveSithContext, LiveDroidContext {

    private val host = env.getRequiredProperty("spring.data.mongodb.host")
    private val port = env.getProperty("spring.data.mongodb.port") ?: 27017

    override val databaseClient = KMongo.createClient(ConnectionString("mongodb://$host:$port")).coroutine
    override val logger: Logger = LiveLogger

}
