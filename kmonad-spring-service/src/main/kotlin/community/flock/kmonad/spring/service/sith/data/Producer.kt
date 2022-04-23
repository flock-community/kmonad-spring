package community.flock.kmonad.spring.service.sith.data

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.sith.model.Sith
import community.flock.kmonad.spring.api.data.ImmutableSith
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import community.flock.kmonad.spring.api.data.Sith as ProducedSith

object Producer {

    fun forSith() = object : Producible<Sith, ProducedSith> {
        override suspend fun Sith.produce() = toProducedSith()
    }

    fun forSithFlow() = object : Producible<Flow<Sith>, List<ProducedSith>> {
        override suspend fun Flow<Sith>.produce() = map { it.toProducedSith() }.toList()
    }

}

private fun Sith.toProducedSith(): ProducedSith = ImmutableSith.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
