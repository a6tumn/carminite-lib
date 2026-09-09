package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.Holder;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.enchantment.Enchantment;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AnvilMenu.class)
public abstract class AnvilMenuMixin extends ItemCombinerMenu {
    public AnvilMenuMixin(@Nullable MenuType<?> menuType, int containerId, Inventory inventory, ContainerLevelAccess access, ItemCombinerMenuSlotDefinition itemInputSlots) {
        super(menuType, containerId, inventory, access, itemInputSlots);
    }

    @Shadow
    private @Nullable String itemName;

    @WrapMethod(method = "createResult()V")
    private void carminite$createResult(Operation<Void> original) {
        original.call();
        ItemStack leftInput = this.inputSlots.getItem(0);
        ItemStack rightInput = this.inputSlots.getItem(1);
        CommonHooks.onAnvilUpdate((AnvilMenu) (Object) this, leftInput, rightInput, resultSlots, this.itemName, this.player);
    }

    @WrapOperation(
        method = "createResult()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/enchantment/Enchantment;canEnchant(Lnet/minecraft/world/item/ItemStack;)Z"
        )
    )
    private boolean carminite$supportsEnchantment(
        Enchantment instance,
        ItemStack itemStack,
        Operation<Boolean> original,
        @Local(name = "enchantmentHolder") Holder<Enchantment> enchantmentHolder
    ) {
        return itemStack.carminite$supportsEnchantment(enchantmentHolder);
    }
}