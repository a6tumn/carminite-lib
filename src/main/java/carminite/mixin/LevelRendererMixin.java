package carminite.mixin;

import carminite.events.modified.CarminiteRenderLevelStageEvent;
import carminite.util.LevelRendererFrustumHolder;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.buffers.GpuBufferSlice;
import net.minecraft.client.Camera;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.util.profiling.Profiler;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(LevelRenderer.class)
public class LevelRendererMixin {

    @Shadow
    @Final
    private LevelRenderState levelRenderState;

    @WrapOperation(
        method = "extractLevel(Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/LevelRenderer;extractVisibleBlockEntities(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/state/level/LevelRenderState;)V"
        )
    )
    private void carminite$extractFrustum(
        LevelRenderer instance,
        Camera camera,
        float deltaPartialTick,
        LevelRenderState levelRenderState,
        Operation<Void> original,
        @Local(name = "cullFrustum") Frustum cullFrustum
    ) {
        LevelRendererFrustumHolder.setFrustum(cullFrustum);
        try {
            original.call(instance, camera, deltaPartialTick, levelRenderState);
        } finally {
            LevelRendererFrustumHolder.clear();
        }
    }

    @Inject(
        method = "lambda$addWeatherPass$0",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/WeatherEffectRenderer;render(Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/client/renderer/state/level/WeatherRenderState;)V",
            shift = At.Shift.AFTER
        )
    )
    private void carminite$afterWeather(
        GpuBufferSlice fog,
        int renderDistance,
        CallbackInfo ci
    ) {
        Profiler.get().push("carminite_render_after_weather");
        new CarminiteRenderLevelStageEvent.AfterWeather((LevelRenderer) (Object) this, this.levelRenderState, null, this.levelRenderState.cameraRenderState.viewRotationMatrix).post();
        Profiler.get().pop();
    }
}