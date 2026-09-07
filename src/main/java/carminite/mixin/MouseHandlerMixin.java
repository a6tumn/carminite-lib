package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.MouseHandler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(MouseHandler.class)
public class MouseHandlerMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @ModifyVariable(
        method = "turnPlayer(D)V",
        at = @At("STORE"),
        name = "ss"
    )
    private double carminite$playerTurn(double ss) {
        var event = ClientHooks.getTurnPlayerValues(this.minecraft.options.sensitivity().get(), this.minecraft.options.smoothCamera);
        return event.getMouseSensitivity() * 0.6F + 0.2F;
    }
}