package carminite.events.api;

import carminite.events.neoforge.AnvilUpdateEvent;
import carminite.events.neoforge.GrindstoneEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class WorkstationEvents {
    public static final Event<AnvilUpdate> ANVIL_UPDATE = EventFactory.createArrayBacked(AnvilUpdate.class, callbacks -> event -> {
        for (AnvilUpdate callback : callbacks) {
            callback.onAnvilUpdate(event);
        }
    });

    public static final Event<GrindstonePlace> GRINDSTONE_PLACE = EventFactory.createArrayBacked(GrindstonePlace.class, callbacks -> event -> {
        for (GrindstonePlace callback : callbacks) {
            callback.onGrindstoneChange(event);
        }
    });

    public static final Event<GrindstoneTake> GRINDSTONE_TAKE = EventFactory.createArrayBacked(GrindstoneTake.class, callbacks -> event -> {
        for (GrindstoneTake callback : callbacks) {
            callback.onGrindstoneTake(event);
        }
    });

    @FunctionalInterface
    public interface AnvilUpdate {
        void onAnvilUpdate(AnvilUpdateEvent event);
    }

    @FunctionalInterface
    public interface GrindstonePlace {
        void onGrindstoneChange(GrindstoneEvent.OnPlaceItem event);
    }

    @FunctionalInterface
    public interface GrindstoneTake {
        void onGrindstoneTake(GrindstoneEvent.OnTakeItem event);
    }
}