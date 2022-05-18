package community.flock.kmonad.spring.service.wielders.data

import community.flock.kmonad.core.common.typeclasses.Producible
import community.flock.kmonad.core.forcewielder.model.ForceWielder
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.DARK
import community.flock.kmonad.core.forcewielder.model.ForceWielder.Type.LIGHT
import community.flock.kmonad.spring.api.data.ImmutableForceWielder
import community.flock.kmonad.spring.api.data.ForceWielder as ProducedForceWielder


object Producer {

    fun forWielder() = object : Producible<ForceWielder, ProducedForceWielder> {
        override suspend fun ForceWielder.produce() = toProducedForceWielder()
    }

    fun forWielders() = object : Producible<List<ForceWielder>, List<ProducedForceWielder>> {
        override suspend fun List<ForceWielder>.produce() = map { it.toProducedForceWielder() }
    }

}

private fun ForceWielder.toProducedForceWielder(): ProducedForceWielder = ImmutableForceWielder.builder()
    .id(id)
    .name(name)
    .age(age)
    .forceType(forceType.produce())
    .build()


private fun ForceWielder.Type.produce(): ProducedForceWielder.Type = when (this) {
    DARK -> ProducedForceWielder.Type.DARK
    LIGHT -> ProducedForceWielder.Type.LIGHT
}
