package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.client.sounds.MusicManager;
import net.minecraft.sounds.Music;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MusicManager.class)
public abstract class MusicManagerMixin {

    @Shadow
    private @Nullable SoundInstance currentMusic;

    @Shadow
    public abstract void stopPlaying();

    @Shadow
    private int nextSongDelay;

    @ModifyExpressionValue(
        method = "tick()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;getSituationalMusic()Lnet/minecraft/sounds/Music;"
        )
    )
    private Music carminite$selectMusic(
        Music original,
        @Share(value = "shouldCancel", namespace = "carminite") LocalBooleanRef shouldCancel
    ) {
        Music newMusic = ClientHooks.selectMusic(original, this.currentMusic);
        if (newMusic == null) {
            if (this.currentMusic != null) {
                this.stopPlaying();
            }
            this.nextSongDelay = 0;
            shouldCancel.set(true);
        }
        return newMusic;
    }

    @Inject(
        method = "tick()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/Minecraft;getSituationalMusic()Lnet/minecraft/sounds/Music;",
            shift = At.Shift.AFTER
        ),
        cancellable = true
    )
    private void carminite$postEventLogic(
        CallbackInfo ci,
        @Share(value = "shouldCancel", namespace = "carminite") LocalBooleanRef shouldCancel
    ) {
       if (shouldCancel.get()) {
           ci.cancel();
       }
    }
}