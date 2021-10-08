package community.flock.kmonad.spring.common

import com.mongodb.assertions.Assertions
import community.flock.kmonad.core.common.define.Logger

object DefaultTestLogger : Logger {

    override fun log(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

    override fun error(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

    override fun warn(s: String) {
        Assertions.assertTrue(s.isNotBlank())
    }

}
