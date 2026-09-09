package carminite.interfaces.extensions;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface IItemStackExtension {
    private ItemStack self() {
        return (ItemStack) this;
    }

    default boolean carminite$supportsEnchantment(Holder<Enchantment> enchantment) {
        return self().getItem().carminite$supportsEnchantment(self(), enchantment);
    }

    default boolean carminite$canWalkOnPowderedSnow(LivingEntity wearer) {
        return self().getItem().carminite$canWalkOnPowderedSnow(self(), wearer);
    }

    default boolean carminite$canFitInsideContainerItems() {
        return self().getItem().carminite$canFitInsideContainerItems(self());
    }
}