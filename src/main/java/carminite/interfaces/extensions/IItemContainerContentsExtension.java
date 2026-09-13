package carminite.interfaces.extensions;

import net.minecraft.world.item.ItemStack;

public interface IItemContainerContentsExtension {
    default int carminite$getSlots() {
        throw new AssertionError("Implemented via mixin");
    }

    default ItemStack carminite$getStackInSlot(int slot) {
        throw new AssertionError("Implemented via mixin");
    }
}