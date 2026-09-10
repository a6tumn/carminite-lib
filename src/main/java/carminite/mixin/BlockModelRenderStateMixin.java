package carminite.mixin;

import carminite.interfaces.extensions.IBlockModelRenderStateExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import it.unimi.dsi.fastutil.ints.IntList;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.block.BlockModelRenderState;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import net.minecraft.client.renderer.special.SpecialModelRenderer;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.List;

@Mixin(BlockModelRenderState.class)
public class BlockModelRenderStateMixin implements IBlockModelRenderStateExtension {

    @Unique
    private boolean carminite$hasTranslucency;

    @Shadow
    private @Nullable List<BlockStateModelPart> modelParts;

    @Shadow
    private @Nullable IntList tintLayers;

    @Shadow
    @Final
    public static int[] EMPTY_TINTS;

    @Shadow
    private @Nullable Matrix4fc transformation;

    @Shadow
    private static void submitSpecialRenderer(SpecialModelRenderer<?> renderer, PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Override
    public void carminite$submitMultiLayer(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor) {
        if (this.modelParts != null && !this.modelParts.isEmpty()) {
            List<BlockStateModelPart> modelPartsCopy = new ObjectArrayList<>(this.modelParts);
            int[] tints = this.tintLayers != null ? this.tintLayers.toArray(EMPTY_TINTS) : EMPTY_TINTS;
            if (this.transformation != null) {
                poseStack.pushPose();
                poseStack.mulPose(this.transformation);
                submitNodeCollector.carminite$submitMultiLayerBlockModel(poseStack, modelPartsCopy, carminite$hasTranslucency, tints, lightCoords, overlayCoords, outlineColor);
                poseStack.popPose();
            } else {
                submitNodeCollector.carminite$submitMultiLayerBlockModel(poseStack, modelPartsCopy, carminite$hasTranslucency, tints, lightCoords, overlayCoords, outlineColor);
            }
        }
        submitSpecialRenderer(null, poseStack, submitNodeCollector, lightCoords, overlayCoords, outlineColor);
    }

    @Inject(
        method = "setupModel(Lorg/joml/Matrix4fc;Z)Ljava/util/List;",
        at = @At(
            value = "FIELD",
            target = "Lnet/minecraft/client/renderer/block/BlockModelRenderState;modelParts:Ljava/util/List;",
            opcode = Opcodes.PUTFIELD,
            shift = At.Shift.BEFORE
        )
    )
    private void carminite$setupModel(
        Matrix4fc transformation,
        boolean hasTranslucency,
        CallbackInfoReturnable<List<BlockStateModelPart>> cir
    ) {
        this.carminite$hasTranslucency = hasTranslucency;
    }

    @Inject(
        method = "clear()V",
        at = @At("HEAD")
    )
    private void carminite$clear(CallbackInfo ci) {
        this.carminite$hasTranslucency = false;
    }
}