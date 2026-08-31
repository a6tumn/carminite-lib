package carminite.events.neoforge;

import carminite.events.api.PlayerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;

public abstract class PlayerEvent extends LivingEvent {
	private final Player player;

	public PlayerEvent(Player player) {
		super(player);
		this.player = player;
	}

	@Override
	public Player getEntity() {
		return player;
	}

	public static class HarvestCheck extends PlayerEvent {
		private final BlockState state;
		private final BlockGetter level;
		private final BlockPos pos;
		private boolean success;

		public HarvestCheck(Player player, BlockState state, BlockGetter level, BlockPos pos, boolean success) {
			super(player);
			this.state = state;
			this.level = level;
			this.pos = pos;
			this.success = success;
		}

		public BlockState getTargetBlock() {
			return this.state;
		}

		public BlockGetter getLevel() {
			return level;
		}

		public BlockPos getPos() {
			return pos;
		}

		public boolean canHarvest() {
			return this.success;
		}

		public void setCanHarvest(boolean success) {
			this.success = success;
		}

		@Override
		public HarvestCheck post() {
			PlayerEvents.HARVEST_CHECK.invoker().doPlayerHarvestCheck(this);
			return this;
		}
	}

	public static class PlayerLoggedInEvent extends PlayerEvent {
		public PlayerLoggedInEvent(Player player) {
			super(player);
		}

		@Override
		public PlayerLoggedInEvent post() {
			PlayerEvents.PLAYER_LOGGED_IN.invoker().firePlayerLoggedIn(this);
			return this;
		}
	}

	public static class PlayerLoggedOutEvent extends PlayerEvent {
		public PlayerLoggedOutEvent(Player player) {
			super(player);
		}

		@Override
		public PlayerLoggedOutEvent post() {
			PlayerEvents.PLAYER_LOGGED_OUT.invoker().firePlayerLoggedOut(this);
			return this;
		}
	}

	public static class PlayerRespawnEvent extends PlayerEvent {
		private final boolean endConquered;

		public PlayerRespawnEvent(Player player, boolean endConquered) {
			super(player);
			this.endConquered = endConquered;
		}

		public boolean isEndConquered() {
			return this.endConquered;
		}

		@Override
		public PlayerRespawnEvent post() {
			PlayerEvents.PLAYER_RESPAWN.invoker().firePlayerRespawnEvent(this);
			return this;
		}
	}
}