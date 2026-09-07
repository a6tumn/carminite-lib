package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.KeyboardHandler;
import net.minecraft.client.input.KeyEvent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(KeyboardHandler.class)
public class KeyboardHandlerMixin {

    @Inject(
        method = "keyPress(JILnet/minecraft/client/input/KeyEvent;)V",
        at = @At("TAIL")
    )
    private void carminite$inputKey(
        long handle,
        int action,
        KeyEvent event,
        CallbackInfo ci,
        @Local(name = "window") Window window
    ) {
        if (handle == window.handle()) {
            ClientHooks.onKeyInput(event, action);
        }
    }
}