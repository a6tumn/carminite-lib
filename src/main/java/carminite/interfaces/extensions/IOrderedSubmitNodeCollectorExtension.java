package carminite.interfaces.extensions;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;

import java.util.List;

public interface IOrderedSubmitNodeCollectorExtension {
    default void carminite$submitMultiLayerBlockModel(
        PoseStack poseStack,
        List<BlockStateModelPart> modelParts,
        boolean translucent,
        int[] tintLayers,
        int lightCoords,
        int overlayCoords,
        int outlineColor
    ) {}
}