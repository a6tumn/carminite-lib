package carminite.mixin;

import net.minecraft.core.Holder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.EquipmentSlotGroup;
import net.minecraft.world.entity.ai.attributes.Attribute;
import net.minecraft.world.entity.ai.attributes.AttributeModifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.enchantment.EnchantmentHelper;
import org.apache.commons.lang3.function.TriConsumer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.function.BiConsumer;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemInstance {

    @Shadow
    public abstract Item getItem();

    @Override
    public int getMaxStackSize() {
        return this.getItem().carminite$getMaxStackSize((ItemStack) (Object) this);
    }

    /**
     * @author Autumn
     * @reason NeoForge completely skips the vanilla logic here, so we will do the same...
     */
    @Overwrite
    public void forEachModifier(final EquipmentSlotGroup slot, final TriConsumer<Holder<Attribute>, AttributeModifier, ItemAttributeModifiers.Display> consumer) {
        ((ItemStack) (Object) this).carminite$getAttributeModifiers().forEach(slot, consumer);
        EnchantmentHelper.forEachModifier(((ItemStack) (Object) this), slot, (a, b) -> consumer.accept(a, b, ItemAttributeModifiers.Display.attributeModifiers()));
    }

    /**
     * @author Autumn
     * @reason ...
     */
    @Overwrite
    public void forEachModifier(final EquipmentSlot slot, final BiConsumer<Holder<Attribute>, AttributeModifier> consumer) {
        ((ItemStack) (Object) this).carminite$getAttributeModifiers().forEach(slot, consumer);
        EnchantmentHelper.forEachModifier(((ItemStack) (Object) this), slot, consumer);
    }
}