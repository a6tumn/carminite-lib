package carminite.events.modified;

import carminite.events.api.ClientEvents;
import carminite.events.neoforge.ViewportEvent;
import net.minecraft.client.Camera;
import net.minecraft.client.Minecraft;
import org.jetbrains.annotations.ApiStatus;
import org.joml.Vector4f;

public class CarminiteComputeFogColorEvent extends ViewportEvent {
    private final Vector4f color;

    @ApiStatus.Internal
    public CarminiteComputeFogColorEvent(Camera camera, float partialTicks, Vector4f color) {
        super(Minecraft.getInstance().gameRenderer, camera, partialTicks);
        this.color = color;
    }

    public float getRed() {
        return this.color.x;
    }

    public void setRed(float red) {
        this.color.x = red;
    }

    public float getGreen() {
        return this.color.y;
    }

    public void setGreen(float green) {
        this.color.y = green;
    }

    public float getBlue() {
        return this.color.z;
    }

    public void setBlue(float blue) {
        this.color.z = blue;
    }

    @Override
    public CarminiteComputeFogColorEvent post() {
        ClientEvents.CARMINITE_COMPUTE_FOG_COLOR.invoker().getFogColor(this);
        return this;
    }
}