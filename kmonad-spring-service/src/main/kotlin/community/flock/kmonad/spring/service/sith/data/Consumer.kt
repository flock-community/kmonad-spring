package community.flock.kmonad.spring.service.sith.data

import community.flock.kmonad.core.sith.data.Sith
import community.flock.kmonad.spring.api.data.Sith as PotentialSith

fun PotentialSith.consume() = Sith(id = id(), name = name(), age = age())
