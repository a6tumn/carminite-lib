package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.BundleContents;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(BundleContents.class)
public class BundleContentsMixin {

    @WrapMethod(method = "canItemBeInBundle(Lnet/minecraft/world/item/ItemStack;)Z")
    private static boolean carminite$canItemBeInBundle(
        ItemStack itemToAdd,
        Operation<Boolean> original
    ) {
        return !itemToAdd.isEmpty() && itemToAdd.carminite$canFitInsideContainerItems();
    }
}