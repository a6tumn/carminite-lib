package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import carminite.interfaces.extensions.IGrindstoneMenuExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.world.Container;
import net.minecraft.world.inventory.GrindstoneMenu;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(GrindstoneMenu.class)
public class GrindstoneMenuMixin implements IGrindstoneMenuExtension {

    @Unique
    private int carminite$xp = -1;

    @Shadow
    @Final
    public Container repairSlots;

    @Shadow
    @Final
    private Container resultSlots;

    @Override
    public int carminite$getXp() {
        return this.carminite$xp;
    }

    @WrapOperation(
        method = "createResult()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/Container;setItem(ILnet/minecraft/world/item/ItemStack;)V"
        )
    )
    private void carminite$onPlaceItem(
        Container instance,
        int i,
        ItemStack itemStack,
        Operation<Void> original
    ) {
        this.carminite$xp = CommonHooks.onGrindstoneChange(this.repairSlots.getItem(0), this.repairSlots.getItem(1), this.resultSlots, -1);
        if (this.carminite$xp == Integer.MIN_VALUE) {
            original.call(instance, i, itemStack);
        }
    }
}