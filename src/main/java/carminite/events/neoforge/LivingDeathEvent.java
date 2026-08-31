package carminite.events.neoforge;

import carminite.events.ICancellableEvent;
import carminite.events.api.LivingEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;

public class LivingDeathEvent extends LivingEvent implements ICancellableEvent {
	private final DamageSource source;

	public LivingDeathEvent(LivingEntity entity, DamageSource source) {
		super(entity);
		this.source = source;
	}

	public DamageSource getSource() {
		return source;
	}

	@Override
	public LivingDeathEvent post() {
		LivingEvents.LIVING_DEATH.invoker().onLivingDeath(this);
		return this;
	}
}
