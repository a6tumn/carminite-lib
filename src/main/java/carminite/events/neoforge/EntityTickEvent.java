package carminite.events.neoforge;

import carminite.events.ICancellableEvent;
import carminite.events.api.TickEvents;
import net.minecraft.world.entity.Entity;

public abstract class EntityTickEvent extends EntityEvent {
	protected EntityTickEvent(Entity entity) {
		super(entity);
	}

	public static class Pre extends EntityTickEvent implements ICancellableEvent {
		public Pre(Entity entity) {
			super(entity);
		}

		@Override
		public void setCanceled(boolean canceled) {
			ICancellableEvent.super.setCanceled(canceled);
		}

		@Override
		public Pre post() {
			TickEvents.ENTITY_TICK_PRE.invoker().fireEntityTickPre(this);
			return this;
		}
	}

	public static class Post extends EntityTickEvent {
		public Post(Entity entity) {
			super(entity);
		}

		@Override
		public Post post() {
			TickEvents.ENTITY_TICK_POST.invoker().fireEntityTickPost(this);
			return this;
		}
	}
}