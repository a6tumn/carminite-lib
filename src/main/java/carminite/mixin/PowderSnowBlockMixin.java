package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.PowderSnowBlock;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(PowderSnowBlock.class)
public class PowderSnowBlockMixin {

    @WrapOperation(
        method = "canEntityWalkOnPowderSnow(Lnet/minecraft/world/entity/Entity;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;is(Ljava/lang/Object;)Z"
        )
    )
    private static boolean carminite$checkIfEntityCanWalkOnSnow(
        ItemStack instance,
        Object o,
        Operation<Boolean> original,
        @Local(argsOnly = true, name = "entity") Entity entity
    ) {
        if (entity instanceof LivingEntity wearer) {
            return instance.carminite$canWalkOnPowderedSnow(wearer);
        }
        return original.call(instance, o);
    }
}