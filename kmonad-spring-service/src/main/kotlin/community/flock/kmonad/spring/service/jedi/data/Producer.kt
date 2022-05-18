package community.flock.kmonad.spring.service.jedi.data

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.spring.api.data.ImmutableJedi
import community.flock.kmonad.spring.api.data.Jedi as ProducedJedi

object Producer {

    fun forSingleJedi() = object : Producible<Jedi, ProducedJedi> {
        override suspend fun Jedi.produce() = toProducedJedi()
    }

    fun forMultipleJedi() = object : Producible<List<Jedi>, List<ProducedJedi>> {
        override suspend fun List<Jedi>.produce() = map { it.toProducedJedi() }
    }

}

private fun Jedi.toProducedJedi(): ProducedJedi = ImmutableJedi.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
