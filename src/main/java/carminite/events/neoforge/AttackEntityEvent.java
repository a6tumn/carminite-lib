package carminite.events.neoforge;

import carminite.events.ICancellableEvent;
import carminite.events.api.PlayerEvents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;

public class AttackEntityEvent extends PlayerEvent implements ICancellableEvent {
	private final Entity target;

	public AttackEntityEvent(Player player, Entity target) {
		super(player);
		this.target = target;
	}

	public Entity getTarget() {
		return target;
	}

	@Override
	public AttackEntityEvent post() {
		PlayerEvents.ATTACK_ENTITY.invoker().onPlayerAttackTarget(this);
		return this;
	}
}