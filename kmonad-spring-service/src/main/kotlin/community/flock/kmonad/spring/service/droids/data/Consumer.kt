package community.flock.kmonad.spring.service.droids.data

import community.flock.kmonad.core.droids.data.Droid
import community.flock.kmonad.spring.api.data.Droid.Type.ASTROMECH
import community.flock.kmonad.spring.api.data.Droid.Type.PROTOCOL
import community.flock.kmonad.spring.api.data.Droid as PotentialDroid

fun PotentialDroid.consume() = Droid(id = id(), designation = designation(), type = type().consume())


private fun PotentialDroid.Type.consume() = when (this) {
    ASTROMECH -> Droid.Type.Astromech
    PROTOCOL -> Droid.Type.Protocol
}
