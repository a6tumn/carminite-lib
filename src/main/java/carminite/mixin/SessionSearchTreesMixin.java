package carminite.mixin;

import carminite.util.ClientTooltipFlag;
import net.minecraft.client.multiplayer.SessionSearchTrees;
import net.minecraft.world.item.TooltipFlag;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(SessionSearchTrees.class)
public class SessionSearchTreesMixin {

    @ModifyVariable(
        method = "lambda$updateRecipes$0",
        at = @At("STORE"),
        name = "tooltipFlag"
    )
    private TooltipFlag carminite$updateRecipes(TooltipFlag tooltipFlag) {
        return ClientTooltipFlag.of(tooltipFlag);
    }

    @ModifyVariable(
        method = "lambda$updateCreativeTooltips$0",
        at = @At("STORE"),
        name = "tooltipFlag"
    )
    private TooltipFlag carminite$updateCreativeTooltips(TooltipFlag tooltipFlag) {
        return ClientTooltipFlag.of(tooltipFlag);
    }
}