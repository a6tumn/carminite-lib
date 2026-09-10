package carminite.interfaces.extensions;

import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.SubmitNodeCollector;

public interface IBlockModelRenderStateExtension {
    default void carminite$submitMultiLayer(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, int overlayCoords, int outlineColor) {
        throw new AssertionError("Implemented via mixin");
    }
}