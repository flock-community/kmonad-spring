package community.flock.kmonad.spring.service.droids.data

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.droid.model.Droid
import community.flock.kmonad.core.droid.model.Droid.Type.Astromech
import community.flock.kmonad.core.droid.model.Droid.Type.Protocol
import community.flock.kmonad.spring.api.data.ImmutableDroid
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import community.flock.kmonad.spring.api.data.Droid as ProducedDroid

object Producer {

    fun forDroid() = object : Producible<Droid, ProducedDroid> {
        override suspend fun Droid.produce() = toProducedDroid()
    }

    fun forDroidFlow() = object : Producible<Flow<Droid>, List<ProducedDroid>> {
        override suspend fun Flow<Droid>.produce() = map { it.toProducedDroid() }.toList()
    }

}

fun Droid.toProducedDroid(): ProducedDroid = ImmutableDroid.builder()
    .id(id)
    .designation(designation)
    .type(type.produce())
    .build()


private fun Droid.Type.produce() = when (this) {
    Astromech -> ProducedDroid.Type.ASTROMECH
    Protocol -> ProducedDroid.Type.PROTOCOL
}
