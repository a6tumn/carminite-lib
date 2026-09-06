package carminite.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import net.minecraft.world.item.enchantment.EnchantmentInstance;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.List;
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
        Stream<Holder<Enchantment>> instance,
        Predicate<? super Object> predicate,
        @Local(argsOnly = true, name = "value") int value,
        @Local(argsOnly = true, name = "itemStack") ItemStack itemStack,
        @Local(name = "results") List<EnchantmentInstance> results
    ) {
        instance.filter(enchantment -> itemStack.getItem().carminite$isPrimaryItemFor(itemStack, enchantment)).forEach(holder -> {
            Enchantment enchantment = holder.value();
            for (int level = enchantment.getMaxLevel(); level >= enchantment.getMinLevel(); level--) {
                if (value >= enchantment.getMinCost(level) && value <= enchantment.getMaxCost(level)) {
                    results.add(new EnchantmentInstance(holder, level));
                    break;
                }
            }
        });
        return null;
    }
}