package carminite;

import carminite.multipart.IMultiPartEntity;
import carminite.multipart.PartEntity;
import net.fabricmc.api.ClientModInitializer;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientEntityEvents;

public class CarminiteClient implements ClientModInitializer {

    @Override
    public void onInitializeClient() {
        registerClientMultipartEvents();
    }

    private static void registerClientMultipartEvents() {
        ClientEntityEvents.ENTITY_LOAD.register((entity, world) -> {
            if (entity instanceof IMultiPartEntity partEntity && partEntity.isMultipartEntity()) {
                for (PartEntity<?> part : partEntity.getParts()) {
                    world.carminite$getPartEntityMap().put(part.getId(), part);
                }
            }
        });
        ClientEntityEvents.ENTITY_UNLOAD.register((entity, world) -> {
            if (entity instanceof IMultiPartEntity partEntity && partEntity.isMultipartEntity()) {
                for (PartEntity<?> part : partEntity.getParts()) {
                    world.carminite$getPartEntityMap().remove(part.getId());
                }
            }
        });
    }
}