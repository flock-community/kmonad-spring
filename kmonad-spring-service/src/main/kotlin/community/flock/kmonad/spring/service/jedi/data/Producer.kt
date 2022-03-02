package community.flock.kmonad.spring.service.jedi.data

import community.flock.kmonad.core.jedi.model.Jedi
import community.flock.kmonad.spring.api.data.ImmutableJedi
import community.flock.kmonad.spring.api.data.Jedi as ProducedJedi

fun List<Jedi>.produce() = map { it.produce() }

fun Jedi.produce(): ProducedJedi = ImmutableJedi.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
