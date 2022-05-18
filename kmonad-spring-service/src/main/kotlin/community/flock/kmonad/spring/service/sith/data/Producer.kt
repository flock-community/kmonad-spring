package community.flock.kmonad.spring.service.sith.data

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.sith.model.Sith
import community.flock.kmonad.spring.api.data.ImmutableSith
import community.flock.kmonad.spring.api.data.Sith as ProducedSith

object Producer {

    fun forSingleSith() = object : Producible<Sith, ProducedSith> {
        override suspend fun Sith.produce() = toProducedSith()
    }

    fun forMultipleSith() = object : Producible<List<Sith>, List<ProducedSith>> {
        override suspend fun List<Sith>.produce() = map { it.toProducedSith() }
    }

}

private fun Sith.toProducedSith(): ProducedSith = ImmutableSith.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
