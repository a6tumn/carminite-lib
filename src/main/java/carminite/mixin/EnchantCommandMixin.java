package carminite.mixin;

import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.server.commands.EnchantCommand;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(EnchantCommand.class)
public class EnchantCommandMixin {

    @WrapOperation(
        method = "enchant(Lnet/minecraft/commands/CommandSourceStack;Ljava/util/Collection;Lnet/minecraft/core/Holder;I)I",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"
        )
    )
    private static boolean carminite$supportsEnchantment(
        Enchantment instance,
        ItemStack itemStack,
        Operation<Boolean> original,
        @Local(argsOnly = true, name = "enchantmentHolder") Holder<Enchantment> enchantmentHolder
    ) {
        return itemStack.carminite$supportsEnchantment(enchantmentHolder);
    }
}