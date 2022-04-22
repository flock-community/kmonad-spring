package community.flock.kmonad.spring.service.jedi.data

import community.flock.kmonad.core.common.Producible
import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.spring.api.data.ImmutableJedi
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import community.flock.kmonad.spring.api.data.Jedi as ProducedJedi

object Producer {

    fun forJedi() = object : Producible<Jedi, ProducedJedi> {
        override suspend fun Jedi.produce() = toProducedJedi()
    }

    fun forJediFlow() = object : Producible<Flow<Jedi>, List<ProducedJedi>> {
        override suspend fun Flow<Jedi>.produce() = map { it.toProducedJedi() }.toList()
    }

}

private fun Jedi.toProducedJedi(): ProducedJedi = ImmutableJedi.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
