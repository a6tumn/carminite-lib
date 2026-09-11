package carminite.interfaces.extensions;

import carminite.events.hooks.CommonHooks;
import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
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

    default boolean carminite$isPrimaryItemFor(Holder<Enchantment> enchantment) {
        return self().getItem().carminite$isPrimaryItemFor(self(), enchantment);
    }

    default ItemAttributeModifiers carminite$getAttributeModifiers() {
        ItemAttributeModifiers defaultModifiers = self().getOrDefault(DataComponents.ATTRIBUTE_MODIFIERS, ItemAttributeModifiers.EMPTY);

        if (defaultModifiers.modifiers().isEmpty()) {
            defaultModifiers = self().getItem().carminite$getDefaultAttributeModifiers(self());
        }

        return CommonHooks.computeModifiedAttributes(self(), defaultModifiers);
    }
}