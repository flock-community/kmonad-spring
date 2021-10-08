package community.flock.kmonad.spring.common

import community.flock.kmonad.core.common.define.Logger

object LiveLogger : Logger {

    override fun error(s: String) = System.err.println("ERROR: $s")


    override fun log(s: String) = println("LOG: $s")


    override fun warn(s: String) = println("WARN: $s")

}
