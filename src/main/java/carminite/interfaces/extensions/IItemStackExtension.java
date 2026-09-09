package carminite.interfaces.extensions;

import net.minecraft.core.Holder;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;

public interface IItemStackExtension {
    private ItemStack self() {
        return (ItemStack) this;
    }

    default boolean carminite$supportsEnchantment(Holder<Enchantment> enchantment) {
        return self().getItem().carminite$supportsEnchantment(self(), enchantment);
    }
}