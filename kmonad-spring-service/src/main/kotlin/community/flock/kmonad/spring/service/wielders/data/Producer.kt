package community.flock.kmonad.spring.service.wielders.data

import community.flock.kmonad.core.wielders.model.ForceWielder
import community.flock.kmonad.core.wielders.model.ForceWielder.Type.DARK
import community.flock.kmonad.core.wielders.model.ForceWielder.Type.LIGHT
import community.flock.kmonad.spring.api.data.ImmutableForceWielder
import community.flock.kmonad.spring.api.data.ForceWielder as ProducedForceWielder

fun List<ForceWielder>.produce(): List<ProducedForceWielder> = map { it.produce() }

fun ForceWielder.produce(): ProducedForceWielder = ImmutableForceWielder.builder()
    .id(id)
    .name(name)
    .age(age)
    .forceType(forceType.produce())
    .build()


private fun ForceWielder.Type.produce(): ProducedForceWielder.Type = when (this) {
    DARK -> ProducedForceWielder.Type.DARK
    LIGHT -> ProducedForceWielder.Type.LIGHT
}
