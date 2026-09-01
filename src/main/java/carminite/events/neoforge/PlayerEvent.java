package carminite.events.neoforge;

import carminite.events.api.PlayerEvents;
import net.minecraft.core.BlockPos;
import net.minecraft.world.Container;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
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

	public static class ItemCraftedEvent extends PlayerEvent {
		private final ItemStack crafting;
		private final Container craftMatrix;

		public ItemCraftedEvent(Player player, ItemStack crafting, Container craftMatrix) {
			super(player);
			this.crafting = crafting;
			this.craftMatrix = craftMatrix;
		}

		public ItemStack getCrafting() {
			return this.crafting;
		}

		public Container getInventory() {
			return this.craftMatrix;
		}

		@Override
		public ItemCraftedEvent post() {
			PlayerEvents.ITEM_CRAFTED.invoker().firePlayerCraftingEvent(this);
			return this;
		}
	}
}