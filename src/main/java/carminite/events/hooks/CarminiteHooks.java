package carminite.events.hooks;

import carminite.events.modified.CarminiteComputeFogColorEvent;
import carminite.events.modified.CarminiteEntityTeleportEvent;
import net.minecraft.client.Camera;
import net.minecraft.world.entity.Entity;
import org.joml.Vector4f;

public class CarminiteHooks {
    public static CarminiteEntityTeleportEvent onEntityTeleport(Entity entity) {
        CarminiteEntityTeleportEvent event = new CarminiteEntityTeleportEvent(entity);
        event.post();
        return event;
    }

    public static void getFogColor(Camera camera, float partialTick, float fogRed, float fogGreen, float fogBlue, Vector4f dest) {
        dest.set(fogRed, fogGreen, fogBlue, 1F);
        CarminiteComputeFogColorEvent event = new CarminiteComputeFogColorEvent(camera, partialTick, dest);
        event.post();
    }


}
