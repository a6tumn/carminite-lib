package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.api.ClientEvents;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import net.minecraft.client.renderer.GameRenderer;
import org.jetbrains.annotations.ApiStatus;

public abstract class ViewportEvent extends CarminiteEvent {
    private final GameRenderer renderer;
    private final Camera camera;
    private final double partialTick;

    @ApiStatus.Internal
    public ViewportEvent(GameRenderer renderer, Camera camera, double partialTick) {
        this.renderer = renderer;
        this.camera = camera;
        this.partialTick = partialTick;
    }

    public GameRenderer getRenderer() {
        return renderer;
    }

    public Camera getCamera() {
        return camera;
    }

    public double getPartialTick() {
        return partialTick;
    }

    public static class ComputeCameraAngles extends ViewportEvent {
        private float yaw;
        private float pitch;
        private float roll;

        @ApiStatus.Internal
        public ComputeCameraAngles(Camera camera, double renderPartialTicks, float yaw, float pitch, float roll) {
            super(Minecraft.getInstance().gameRenderer, camera, renderPartialTicks);
            this.setYaw(yaw);
            this.setPitch(pitch);
            this.setRoll(roll);
        }

        public float getYaw() {
            return yaw;
        }

        public void setYaw(float yaw) {
            this.yaw = yaw;
        }

        public float getPitch() {
            return pitch;
        }

        public void setPitch(float pitch) {
            this.pitch = pitch;
        }

        public float getRoll() {
            return roll;
        }

        public void setRoll(float roll) {
            this.roll = roll;
        }

        @Override
        public ComputeCameraAngles post() {
            ClientEvents.COMPUTE_CAMERA_ANGLES.invoker().computeCameraAngles(this);
            return this;
        }
    }
}