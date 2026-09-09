package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.level.storage.loot.functions.EnchantRandomlyFunction;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantRandomlyFunction.class)
public class EnchantRandomlyFunctionMixin {

    @WrapOperation(
        method = "lambda$run$1",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"
        )
    )
    private static boolean carminite$supportsEnchantment(
        Enchantment instance,
        ItemStack itemStack,
        Operation<Boolean> original,
        @Local(argsOnly = true, name = "candidate") Holder<Enchantment> candidate
    ) {
        return itemStack.carminite$supportsEnchantment(candidate);
    }
}