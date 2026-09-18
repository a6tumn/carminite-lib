package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.ICancellableEvent;
import carminite.events.api.ClientEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.player.AbstractClientPlayer;
import net.minecraft.client.renderer.SubmitNodeCollector;
import net.minecraft.world.entity.HumanoidArm;
import org.jetbrains.annotations.ApiStatus;

public class RenderArmEvent extends CarminiteEvent implements ICancellableEvent {
    private final PoseStack poseStack;
    private final SubmitNodeCollector submitNodeCollector;
    private final int packedLight;
    private final AbstractClientPlayer player;
    private final HumanoidArm arm;

    @ApiStatus.Internal
    public RenderArmEvent(PoseStack poseStack, SubmitNodeCollector submitNodeCollector, int packedLight, AbstractClientPlayer player, HumanoidArm arm) {
        this.poseStack = poseStack;
        this.submitNodeCollector = submitNodeCollector;
        this.packedLight = packedLight;
        this.player = player;
        this.arm = arm;
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

    public int getPackedLight() {
        return packedLight;
    }

    public AbstractClientPlayer getPlayer() {
        return player;
    }

    @Override
    public RenderArmEvent post() {
        ClientEvents.RENDER_ARM.invoker().renderSpecificFirstPersonArm(this);
        return this;
    }
}