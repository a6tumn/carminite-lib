package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.ICancellableEvent;
import carminite.events.api.WorkstationEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;

public abstract class GrindstoneEvent extends CarminiteEvent {
    private final ItemStack top;
    private final ItemStack bottom;
    private int xp;

    protected GrindstoneEvent(ItemStack top, ItemStack bottom, int xp) {
        this.top = top;
        this.bottom = bottom;
        this.xp = xp;
    }

    public ItemStack getTopItem() {
        return top;
    }

    public ItemStack getBottomItem() {
        return bottom;
    }

    public int getXp() {
        return xp;
    }

    public void setXp(int xp) {
        this.xp = xp;
    }

    public static class OnPlaceItem extends GrindstoneEvent implements ICancellableEvent {
        private ItemStack output;

        public OnPlaceItem(ItemStack top, ItemStack bottom, int xp) {
            super(top, bottom, xp);
            this.output = ItemStack.EMPTY;
        }

        public ItemStack getOutput() {
            return output;
        }

        public void setOutput(ItemStack output) {
            this.output = output;
        }

        @Override
        public OnPlaceItem post() {
            WorkstationEvents.GRINDSTONE_PLACE.invoker().onGrindstoneChange(this);
            return this;
        }
    }

    public static class OnTakeItem extends GrindstoneEvent implements ICancellableEvent {
        private ItemStack newTop = ItemStack.EMPTY;
        private ItemStack newBottom = ItemStack.EMPTY;

        private final ContainerLevelAccess access;
        private final Player player;

        public OnTakeItem(ContainerLevelAccess access, Player player, ItemStack top, ItemStack bottom, int xp) {
            super(top, bottom, xp);
            this.access = access;
            this.player = player;
        }

        public ItemStack getNewTopItem() {
            return newTop;
        }

        public ItemStack getNewBottomItem() {
            return newBottom;
        }

        public void setNewTopItem(ItemStack newTop) {
            this.newTop = newTop;
        }

        public void setNewBottomItem(ItemStack newBottom) {
            this.newBottom = newBottom;
        }

        public int getXp() {
            return super.getXp();
        }

        public ContainerLevelAccess getContainerAccess() {
            return this.access;
        }

        public Player getPlayer() {
            return this.player;
        }

        @Override
        public OnTakeItem post() {
            WorkstationEvents.GRINDSTONE_TAKE.invoker().onGrindstoneTake(this);
            return this;
        }
    }
}