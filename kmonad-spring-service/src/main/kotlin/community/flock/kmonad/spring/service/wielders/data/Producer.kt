package community.flock.kmonad.spring.service.wielders.data

import community.flock.kmonad.core.common.Producible
import community.flock.kmonad.core.wielders.model.ForceWielder
import community.flock.kmonad.core.wielders.model.ForceWielder.Type.DARK
import community.flock.kmonad.core.wielders.model.ForceWielder.Type.LIGHT
import community.flock.kmonad.spring.api.data.ImmutableForceWielder
import kotlinx.coroutines.flow.Flow
import kotlinx.coroutines.flow.map
import kotlinx.coroutines.flow.toList
import community.flock.kmonad.spring.api.data.ForceWielder as ProducedForceWielder


object Producer {

    fun forWielder() = object : Producible<ForceWielder, ProducedForceWielder> {
        override suspend fun ForceWielder.produce() = toProducedForceWielder()
    }

    fun forWielderFlow() = object : Producible<Flow<ForceWielder>, List<ProducedForceWielder>> {
        override suspend fun Flow<ForceWielder>.produce() = map { it.toProducedForceWielder() }.toList()
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
