package carminite.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.function.Predicate;
import java.util.stream.Stream;

@Mixin(EnchantmentHelper.class)
public class EnchantmentHelperMixin {

    @Redirect(
        method = "getAvailableEnchantmentResults(ILnet/minecraft/world/item/ItemStack;Ljava/util/stream/Stream;)Ljava/util/List;",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/stream/Stream;filter(Ljava/util/function/Predicate;)Ljava/util/stream/Stream;"
        )
    )
    private static Stream<Holder<Enchantment>> carminite$getAvailableEnchantmentResults(
        Stream<Holder<Enchantment>> source,
        Predicate<? super Holder<Enchantment>> originalPredicate,
        @Local(argsOnly = true, name = "itemStack") ItemStack itemStack
    ) {
        return source.filter(enchantment -> itemStack.getItem().carminite$isPrimaryItemFor(itemStack, enchantment));
    }
}