package community.flock.kmonad.spring.service.droids.data

import community.flock.kmonad.core.droids.data.Droid
import community.flock.kmonad.core.droids.data.Droid.Type.Astromech
import community.flock.kmonad.core.droids.data.Droid.Type.Protocol
import community.flock.kmonad.spring.api.data.ImmutableDroid
import community.flock.kmonad.spring.api.data.Droid as ProducedDroid

fun List<Droid>.produce() = map { it.produce() }

fun Droid.produce(): ProducedDroid = ImmutableDroid.builder()
    .id(id)
    .designation(designation)
    .type(type.produce())
    .build()


private fun Droid.Type.produce() = when (this) {
    Astromech -> ProducedDroid.Type.ASTROMECH
    Protocol -> ProducedDroid.Type.PROTOCOL
}
