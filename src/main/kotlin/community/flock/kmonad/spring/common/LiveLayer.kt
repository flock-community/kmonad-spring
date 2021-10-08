package community.flock.kmonad.spring.common

import com.mongodb.ConnectionString
import community.flock.kmonad.core.common.define.Logger
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import org.springframework.context.annotation.Configuration
import org.springframework.core.env.Environment
import community.flock.kmonad.spring.jedi.LiveRepositoryContext as LiveJediRepositoryContext
import community.flock.kmonad.spring.sith.LiveRepositoryContext as LiveSithRepositoryContext

@Configuration
class LiveLayer(env: Environment) : LiveJediRepositoryContext, LiveSithRepositoryContext {

    private val host = env.getRequiredProperty("db.host")
    private val port = env.getProperty("db.port") ?: 27017

    override val databaseClient = KMongo.createClient(ConnectionString("mongodb://$host:$port")).coroutine
    override val logger: Logger = LiveLogger

}
