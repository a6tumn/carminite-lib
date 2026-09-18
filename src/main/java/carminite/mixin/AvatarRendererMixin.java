package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Minecraft;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.client.renderer.entity.player.AvatarRenderer;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.HumanoidArm;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AvatarRenderer.class)
public class AvatarRendererMixin {

    @WrapWithCondition(
        method = "renderRightHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/player/AvatarRenderer;renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Lnet/minecraft/client/model/geom/ModelPart;Z)V"
        )
    )
    @SuppressWarnings("all")
    private boolean carminite$renderRightHand(
        AvatarRenderer instance,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        Identifier skinTexture,
        ModelPart arm,
        boolean hasSleeve
    ) {

        return !ClientHooks.renderSpecificFirstPersonArm(poseStack, submitNodeCollector, lightCoords, Minecraft.getInstance().player, HumanoidArm.RIGHT);
    }

    @WrapWithCondition(
        method = "renderLeftHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Z)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/renderer/entity/player/AvatarRenderer;renderHand(Lcom/mojang/blaze3d/vertex/PoseStack;Lnet/minecraft/client/renderer/SubmitNodeCollector;ILnet/minecraft/resources/Identifier;Lnet/minecraft/client/model/geom/ModelPart;Z)V"
        )
    )
    @SuppressWarnings("all")
    private boolean carminite$renderLeftHand(
        AvatarRenderer instance,
        PoseStack poseStack,
        SubmitNodeCollector submitNodeCollector,
        int lightCoords,
        Identifier skinTexture,
        ModelPart arm,
        boolean hasSleeve
    ) {
        return !ClientHooks.renderSpecificFirstPersonArm(poseStack, submitNodeCollector, lightCoords, Minecraft.getInstance().player, HumanoidArm.LEFT);
    }
}