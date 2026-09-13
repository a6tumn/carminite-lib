package carminite.mixin;

import carminite.interfaces.extensions.IItemContainerContentsExtension;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.ItemStackTemplate;
import net.minecraft.world.item.component.ItemContainerContents;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;

import java.util.List;
import java.util.Optional;

@Mixin(ItemContainerContents.class)
public class ItemContainerContentsMixin implements IItemContainerContentsExtension {

    @Shadow
    @Final
    private List<Optional<ItemStackTemplate>> items;

    @Override
    public int carminite$getSlots() {
        return this.items.size();
    }

    @Override
    public ItemStack carminite$getStackInSlot(int slot) {
        carminite$validateSlotIndex(slot);
        return this.items.get(slot).map(ItemStackTemplate::create).orElse(ItemStack.EMPTY);
    }

    @Unique
    private void carminite$validateSlotIndex(int slot) {
        if (slot < 0 || slot >= carminite$getSlots()) {
           throw new UnsupportedOperationException("Slot " + slot + " not in valid range - [0," + carminite$getSlots() + ")");
        }
    }
}