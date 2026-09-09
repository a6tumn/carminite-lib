package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.Direction;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.ShulkerBoxBlock;
import net.minecraft.world.level.block.entity.ShulkerBoxBlockEntity;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(ShulkerBoxBlockEntity.class)
public class ShulkerBoxBlockEntityMixin {

    @WrapMethod(method = "canPlaceItemThroughFace(ILnet/minecraft/world/item/ItemStack;Lnet/minecraft/core/Direction;)Z")
    private boolean canPlaceItemThroughFace(
        int slot,
        ItemStack itemStack,
        Direction direction,
        Operation<Boolean> original
    ) {
        return !(Block.byItem(itemStack.getItem()) instanceof ShulkerBoxBlock) && itemStack.carminite$canFitInsideContainerItems();
    }
}