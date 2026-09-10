package carminite.events.modified;

import carminite.events.CarminiteEvent;
import carminite.events.api.ClientEvents;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import org.joml.Matrix4fc;
import org.jspecify.annotations.Nullable;

public abstract class CarminiteRenderLevelStageEvent extends CarminiteEvent {
    private final LevelRenderer levelRenderer;
    private final LevelRenderState levelRenderState;
    private final PoseStack poseStack;
    private final Matrix4fc modelViewMatrix;

    public CarminiteRenderLevelStageEvent(LevelRenderer levelRenderer, LevelRenderState levelRenderState, @Nullable PoseStack poseStack, Matrix4fc modelViewMatrix) {
        this.levelRenderer = levelRenderer;
        this.levelRenderState = levelRenderState;
        this.poseStack = poseStack != null ? poseStack : new PoseStack();
        this.modelViewMatrix = modelViewMatrix;
    }

    public LevelRenderer getLevelRenderer() {
        return levelRenderer;
    }

    public LevelRenderState getLevelRenderState() {
        return levelRenderState;
    }

    public PoseStack getPoseStack() {
        return poseStack;
    }

    public Matrix4fc getModelViewMatrix() {
        return modelViewMatrix;
    }

    public static class AfterWeather extends CarminiteRenderLevelStageEvent {
        public AfterWeather(LevelRenderer levelRenderer, LevelRenderState levelRenderState, @Nullable PoseStack poseStack, Matrix4fc modelViewMatrix) {
            super(levelRenderer, levelRenderState, poseStack, modelViewMatrix);
        }

        @Override
        public AfterWeather post() {
            ClientEvents.CARMINITE_RENDER_LEVEL_AFTER_WEATHER.invoker().afterWeather(this);
            return this;
        }
    }
}