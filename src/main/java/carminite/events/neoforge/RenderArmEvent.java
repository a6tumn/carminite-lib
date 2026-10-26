package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.ICancellableEvent;
import carminite.events.api.ClientEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.entity.ClientAvatarEntity;
import net.minecraft.client.model.geom.ModelPart;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.resources.Identifier;
import net.minecraft.world.entity.Avatar;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.ApiStatus;

public class RenderArmEvent<AvatarlikeEntity extends Avatar & ClientAvatarEntity> extends CarminiteEvent implements ICancellableEvent {
    private final PoseStack poseStack;
    private final SubmitNodeCollector submitNodeCollector;
    private final int lightCoords;
    private final Identifier skinTexture;
    private final boolean hasSleeve;
    private final AvatarlikeEntity avatar;
    private final HumanoidArm arm;
    private final ModelPart armPart;

    @ApiStatus.Internal
    public RenderArmEvent(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int lightCoords, Identifier skinTexture, boolean hasSleeve,
                          AvatarlikeEntity avatar, HumanoidArm arm, ModelPart armPart) {
        this.poseStack = poseStack;
        this.submitNodeCollector = submitNodeCollector;
        this.lightCoords = lightCoords;
        this.skinTexture = skinTexture;
        this.hasSleeve = hasSleeve;
        this.avatar = avatar;
        this.arm = arm;
        this.armPart = armPart;
    }

    public HumanoidArm getArm() {
        return arm;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public SubmitNodeCollector getSubmitNodeCollector() {
        return submitNodeCollector;
    }

    public int getLightCoords() {
        return lightCoords;
    }

    public Identifier getSkinTexture() {
        return skinTexture;
    }

    public boolean hasSleeve() {
        return hasSleeve;
    }

    public ModelPart getArmPart() {
        return armPart;
    }

    public AvatarlikeEntity getAvatar() {
        return avatar;
    }

    @Override
    public RenderArmEvent<AvatarlikeEntity> post() {
        ClientEvents.RENDER_ARM.invoker().renderSpecificFirstPersonArm(this);
        return this;
    }
}