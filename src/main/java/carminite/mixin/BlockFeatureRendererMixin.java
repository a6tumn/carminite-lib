package carminite.mixin;

import carminite.client.ExtendedBlockFeatureRenderer;
import com.mojang.blaze3d.vertex.QuadInstance;
import net.minecraft.client.renderer.MultiBufferSource;
import net.minecraft.client.renderer.OutlineBufferSource;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.block.BlockStateModelSet;
import net.minecraft.client.renderer.feature.BlockFeatureRenderer;
import net.minecraft.client.renderer.state.OptionsRenderState;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(BlockFeatureRenderer.class)
public class BlockFeatureRendererMixin {

    @Shadow
    @Final
    private QuadInstance quadInstance;

    @Inject(
        method = "renderSolid(Lnet/minecraft/client/renderer/SubmitNodeCollection;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/block/BlockStateModelSet;Lnet/minecraft/client/renderer/OutlineBufferSource;Lnet/minecraft/client/renderer/state/OptionsRenderState;)V",
        at = @At("TAIL")
    )
    private void carminite$renderSolid(
        SubmitNodeCollection nodeCollection,
        MultiBufferSource.BufferSource bufferSource,
        BlockStateModelSet blockStateModelSet,
        OutlineBufferSource outlineBufferSource,
        OptionsRenderState optionsState,
        CallbackInfo ci
    ) {
        ExtendedBlockFeatureRenderer.renderMultiLayerBlockModelSubmits(nodeCollection, bufferSource, outlineBufferSource, this.quadInstance, false);
    }

    @Inject(
        method = "renderTranslucent(Lnet/minecraft/client/renderer/SubmitNodeCollection;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/block/BlockStateModelSet;Lnet/minecraft/client/renderer/OutlineBufferSource;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/state/OptionsRenderState;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/feature/BlockFeatureRenderer;renderBlockModelSubmits(Lnet/minecraft/client/renderer/SubmitNodeCollection;Lnet/minecraft/client/renderer/MultiBufferSource$BufferSource;Lnet/minecraft/client/renderer/OutlineBufferSource;Z)V",
            shift = At.Shift.AFTER
        )
    )
    private void carminite$renderTranslucent(
        SubmitNodeCollection nodeCollection,
        MultiBufferSource.BufferSource bufferSource,
        BlockStateModelSet blockStateModelSet,
        OutlineBufferSource outlineBufferSource,
        MultiBufferSource.BufferSource crumblingBufferSource,
        OptionsRenderState optionsState,
        CallbackInfo ci
    ) {
        ExtendedBlockFeatureRenderer.renderMultiLayerBlockModelSubmits(nodeCollection, bufferSource, outlineBufferSource, this.quadInstance, true);
    }
}