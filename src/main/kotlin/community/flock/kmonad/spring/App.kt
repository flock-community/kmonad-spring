package community.flock.kmonad.spring

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.autoconfigure.mongo.MongoReactiveAutoConfiguration
import org.springframework.boot.runApplication

@SpringBootApplication(exclude = [MongoReactiveAutoConfiguration::class])
class App

fun main(args: Array<String>) {
    runApplication<App>(*args)
}
