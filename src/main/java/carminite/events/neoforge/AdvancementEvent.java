package carminite.events.neoforge;

import carminite.events.api.PlayerEvents;
import net.minecraft.advancements.AdvancementHolder;
import net.minecraft.world.entity.player.Player;

public abstract class AdvancementEvent extends PlayerEvent {
	private final AdvancementHolder advancement;

	public AdvancementEvent(Player player, AdvancementHolder advancement) {
		super(player);
		this.advancement = advancement;
	}

	public AdvancementHolder getAdvancement() {
		return advancement;
	}

	public static class AdvancementEarnEvent extends AdvancementEvent {
		public AdvancementEarnEvent(Player player, AdvancementHolder earned) {
			super(player, earned);
		}

		@Override
		public AdvancementEarnEvent post() {
			PlayerEvents.ADVANCEMENT_EARNED.invoker().onAdvancementEarnedEvent(this);
			return this;
		}
	}
}