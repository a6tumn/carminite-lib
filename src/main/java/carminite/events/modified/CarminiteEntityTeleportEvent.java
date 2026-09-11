package carminite.events.modified;

import carminite.events.ICancellableEvent;
import carminite.events.api.EntityEvents;
import carminite.events.neoforge.EntityEvent;
import net.minecraft.world.entity.Entity;

public class CarminiteEntityTeleportEvent extends EntityEvent implements ICancellableEvent {
    public CarminiteEntityTeleportEvent(Entity entity) {
        super(entity);
    }

    @Override
    public CarminiteEntityTeleportEvent post() {
        EntityEvents.CARMINITE_ENTITY_TELEPORT.invoker().onEntityTeleport(this);
        return this;
    }
}