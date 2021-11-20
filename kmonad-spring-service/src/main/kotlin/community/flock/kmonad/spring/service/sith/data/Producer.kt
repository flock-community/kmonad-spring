package community.flock.kmonad.spring.service.sith.data

import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.spring.api.data.ImmutableSith
import community.flock.kmonad.spring.api.data.Sith as ProducedSith

fun List<Sith>.produce() = map { it.produce() }

fun Sith.produce(): ProducedSith = ImmutableSith.builder()
    .id(id)
    .name(name)
    .age(age)
    .build()
