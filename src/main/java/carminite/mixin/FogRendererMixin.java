package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.fog.FogRenderer;
import org.joml.Vector4f;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(FogRenderer.class)
public class FogRendererMixin {

    @Inject(
        method = "computeFogColor(Lnet/minecraft/client/Camera;FLnet/minecraft/client/multiplayer/ClientLevel;IFLorg/joml/Vector4f;)V",
        at = @At(
            value = "INVOKE",
            target = "Lorg/joml/Vector4f;set(FFFF)Lorg/joml/Vector4f;"
        ),
        cancellable = true
    )
    private void carminite$fogColor(
        Camera camera,
        float partialTicks,
        ClientLevel level,
        int renderDistance,
        float darkenWorldAmount,
        Vector4f dest,
        CallbackInfo ci,
        @Local(name = "fogRed") float fogRed,
        @Local(name = "fogGreen") float fogGreen,
        @Local(name = "fogBlue") float fogBlue
    ) {
        ClientHooks.getFogColor(camera, partialTicks, fogRed, fogGreen, fogBlue, dest);
        ci.cancel();
    }
}