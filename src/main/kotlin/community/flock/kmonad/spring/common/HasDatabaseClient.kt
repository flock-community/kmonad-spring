package community.flock.kmonad.spring.common

import org.litote.kmongo.coroutine.CoroutineClient

interface HasDatabaseClient {
    val databaseClient: CoroutineClient
}
