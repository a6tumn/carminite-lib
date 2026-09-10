package carminite.mixin;

import carminite.interfaces.extensions.ISubmitNodeCollectionExtension;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.OrderedSubmitNodeCollector;
import net.minecraft.client.renderer.SubmitNodeCollection;
import net.minecraft.client.renderer.SubmitNodeStorage;
import net.minecraft.client.renderer.block.dispatch.BlockStateModelPart;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.ArrayList;
import java.util.List;

@Mixin(SubmitNodeCollection.class)
public abstract class SubmitNodeCollectionMixin implements OrderedSubmitNodeCollector, ISubmitNodeCollectionExtension {

    @Shadow
    private boolean wasUsed;

    @Unique
    private final List<SubmitNodeStorage.MultiLayerBlockModelSubmit> carminite$multiLayerBlockModelSubmits = new ArrayList<>();

    @Override
    public List<SubmitNodeStorage.MultiLayerBlockModelSubmit> carminite$getMultiLayerBlockModelSubmits() {
        return this.carminite$multiLayerBlockModelSubmits;
    }

    @Override
    public void carminite$submitMultiLayerBlockModel(PoseStack poseStack, List<BlockStateModelPart> modelParts, boolean translucent, int[] tintLayers, int lightCoords, int overlayCoords, int outlineColor) {
        this.wasUsed = true;
        this.carminite$multiLayerBlockModelSubmits.add(new SubmitNodeStorage.MultiLayerBlockModelSubmit(poseStack.last().copy(), modelParts, translucent, tintLayers, lightCoords, overlayCoords, outlineColor));
    }

    @Inject(
        method = "clear()V",
        at = @At(
            value = "INVOKE",
            target = "Ljava/util/List;clear()V",
            ordinal = 7,
            shift = At.Shift.AFTER
        )
    )
    private void carminite$clear(CallbackInfo ci) {
        carminite$multiLayerBlockModelSubmits.clear();
    }
}