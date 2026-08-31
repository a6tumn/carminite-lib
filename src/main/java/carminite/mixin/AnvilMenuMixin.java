package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.world.entity.player.Inventory;
import net.minecraft.world.inventory.*;
import net.minecraft.world.item.ItemStack;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

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
}