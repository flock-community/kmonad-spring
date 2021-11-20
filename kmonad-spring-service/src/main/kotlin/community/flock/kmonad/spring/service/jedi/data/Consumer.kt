package community.flock.kmonad.spring.service.jedi.data

import community.flock.kmonad.core.jedi.data.Jedi
import community.flock.kmonad.spring.api.data.Jedi as PotentialJedi

fun PotentialJedi.consume() = Jedi(id = id(), name = name(), age = age())
