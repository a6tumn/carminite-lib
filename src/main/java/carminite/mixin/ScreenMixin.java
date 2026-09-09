package carminite.mixin;

import carminite.util.ClientTooltipFlag;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.List;

@Mixin(Screen.class)
public class ScreenMixin {

    @ModifyReturnValue(
        method = "getTooltipFromItem(Lnet/minecraft/client/Minecraft;Lnet/minecraft/world/item/ItemStack;)Ljava/util/List;",
        at = @At("RETURN")
    )
    private static List<Component> carminite$getTooltipFromItem(
        List<Component> original,
        @Local(argsOnly = true, name = "minecraft") Minecraft minecraft,
        @Local(argsOnly = true, name = "itemStack") ItemStack itemStack
    ) {
        return itemStack.getTooltipLines(
            Item.TooltipContext.of(minecraft.level),
            minecraft.player,
            ClientTooltipFlag.of(minecraft.options.advancedItemTooltips ? TooltipFlag.Default.ADVANCED : TooltipFlag.Default.NORMAL)
        );
    }
}