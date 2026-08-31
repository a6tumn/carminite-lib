package carminite.events.neoforge;

import carminite.events.api.EntityEvents;
import carminite.events.ICancellableEvent;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LightningBolt;

public class EntityStruckByLightningEvent extends EntityEvent implements ICancellableEvent {
	private final LightningBolt lightning;

	public EntityStruckByLightningEvent(Entity entity, LightningBolt lightning) {
		super(entity);
		this.lightning = lightning;
	}

	public LightningBolt getLightning() {
		return lightning;
	}

	@Override
	public EntityStruckByLightningEvent post() {
		EntityEvents.ENTITY_STRUCK_BY_LIGHTNING.invoker().onEntityStruckByLightning(this);
		return this;
	}
}