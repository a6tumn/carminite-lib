package carminite.interfaces.extensions;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;

import java.util.List;

public interface ISubmitNodeStorageExtension extends SubmitNodeCollector {
    private SubmitNodeStorage self() {
        return (SubmitNodeStorage) this;
    }

    @Override
    default void carminite$submitMultiLayerBlockModel(
        PoseStack poseStack,
        List<BlockStateModelPart> modelParts,
        boolean translucent,
        int[] tintLayers,
        int lightCoords,
        int overlayCoords,
        int outlineColor
    ) {
        self().order(0).carminite$submitMultiLayerBlockModel(poseStack, modelParts, translucent, tintLayers, lightCoords, overlayCoords, outlineColor);
    }

    record MultiLayerBlockModelSubmit(
        PoseStack.Pose pose,
        List<BlockStateModelPart> modelParts,
        boolean translucent,
        int[] tintLayers,
        int lightCoords,
        int overlayCoords,
        int outlineColor) {}
}
