package carminite.mixin;

import carminite.events.hooks.EventHooks;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.CraftingContainer;
import net.minecraft.world.inventory.ResultSlot;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ResultSlot.class)
public class ResultSlotMixin {

    @Shadow
    @Final
    private Player player;

    @Shadow
    @Final
    private CraftingContainer craftSlots;

    @Inject(
        method = "checkTakeAchievements(Lnet/minecraft/world/item/ItemStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/ItemStack;onCraftedBy(Lnet/minecraft/world/entity/player/Player;I)V",
            shift = At.Shift.AFTER
        )
    )
    private void carminite$playerItemCrafted(
        ItemStack carried,
        CallbackInfo ci
    ) {
        EventHooks.firePlayerCraftingEvent(this.player, carried, this.craftSlots);
    }
}