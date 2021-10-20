package community.flock.kmonad.spring.common

import community.flock.kmonad.core.common.define.Logger
import org.junit.jupiter.api.Assertions

object DefaultTestLogger : Logger {

    override fun log(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

    override fun error(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

    override fun warn(string: String) {
        Assertions.assertTrue(string.isNotBlank())
    }

}
