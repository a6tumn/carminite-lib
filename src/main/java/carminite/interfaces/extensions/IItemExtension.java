package carminite.interfaces.extensions;

import net.minecraft.core.Holder;
import net.minecraft.core.component.DataComponents;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jetbrains.annotations.ApiStatus;

public interface IItemExtension {
	default int carminite$getMaxStackSize(ItemStack stack) {
		return stack.getOrDefault(DataComponents.MAX_STACK_SIZE, 1);
	}

	default boolean carminite$onLeftClickEntity(ItemStack stack, Player player, Entity entity) {
		return false;
	}

	@ApiStatus.OverrideOnly
	default boolean carminite$supportsEnchantment(ItemStack stack, Holder<Enchantment> enchantment) {
		return stack.is(Items.ENCHANTED_BOOK) || enchantment.value().isSupportedItem(stack);
	}

	default boolean carminite$canWalkOnPowderedSnow(ItemStack stack, LivingEntity wearer) {
		return stack.is(Items.LEATHER_BOOTS);
	}
}