package community.flock.kmonad.spring.common

import com.mongodb.ConnectionString
import community.flock.kmonad.spring.jedi.LiveRepositoryContext
import org.litote.kmongo.coroutine.coroutine
import org.litote.kmongo.reactivestreams.KMongo

class LiveLayer(host: String) : LiveRepositoryContext {

    override val databaseClient = KMongo.createClient(ConnectionString("mongodb://$host")).coroutine

}
