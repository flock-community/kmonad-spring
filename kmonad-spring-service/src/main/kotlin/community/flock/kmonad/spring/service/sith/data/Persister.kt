package community.flock.kmonad.spring.service.sith.data

import community.flock.kmonad.core.sith.model.Sith

fun Sith.persist() = PersistedSith(
    id = id,
    name = name,
    age = age,
)

fun PersistedSith.internalize() = Sith(
    id = id,
    name = name,
    age = age,
)
