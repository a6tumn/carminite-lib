package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.inventory.ShulkerBoxSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxSlot.class)
public class ShulkerBoxSlotMixin {

    @WrapMethod(method = "mayPlace(Lnet/minecraft/world/item/ItemStack;)Z")
    private boolean carminite$mayPlace(
        ItemStack itemStack,
        Operation<Boolean> original
    ) {
        return itemStack.getItem().carminite$canFitInsideContainerItems(itemStack);
    }
}