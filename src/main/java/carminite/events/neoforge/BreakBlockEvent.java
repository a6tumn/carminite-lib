package carminite.events.neoforge;

import carminite.events.api.BlockEvents;
import carminite.events.ICancellableEvent;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;

public class BreakBlockEvent extends BlockEvent implements ICancellableEvent {
	private final Player player;
	private boolean notifyClient = false;

	public BreakBlockEvent(Level level, BlockPos pos, BlockState state, Player player) {
		super(level, pos, state);
		this.player = player;
	}

	public Player getPlayer() {
		return player;
	}

	@Override
	public void setCanceled(boolean canceled) {
		ICancellableEvent.super.setCanceled(canceled);
	}

	public boolean shouldNotifyClient() {
		return notifyClient;
	}

	public void setNotifyClient(boolean notify) {
		this.notifyClient = notify;
	}

	@Override
	public BreakBlockEvent post() {
		BlockEvents.BREAK_BLOCK.invoker().fireBlockBreak(this);
		return this;
	}
}