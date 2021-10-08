package community.flock.kmonad.spring.common

import com.mongodb.ConnectionString
import community.flock.kmonad.core.common.define.Logger
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo
import community.flock.kmonad.spring.jedi.LiveRepositoryContext as LiveJediRepositoryContext
import community.flock.kmonad.spring.sith.LiveRepositoryContext as LiveSithRepositoryContext

class LiveLayer(host: String) : LiveJediRepositoryContext, LiveSithRepositoryContext {

    override val databaseClient = KMongo.createClient(ConnectionString("mongodb://$host")).coroutine
    override val logger: Logger = LiveLogger

}
