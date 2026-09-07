package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.player.LocalPlayer;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LocalPlayer.class)
public class LocalPlayerMixin {

    @Shadow
    public ClientInput input;

    @Inject(
        method = "aiStep()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/player/ClientInput;tick()V",
            shift = At.Shift.AFTER
        )
    )
    private void carminite$movementUpdate(CallbackInfo ci) {
        ClientHooks.onMovementInputUpdate((LocalPlayer) (Object) this, this.input);
    }
}