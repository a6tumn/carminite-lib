package carminite.events.api;

import carminite.events.neoforge.AnvilUpdateEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class WorkstationEvents {
    public static final Event<AnvilUpdate> ANVIL_UPDATE = EventFactory.createArrayBacked(AnvilUpdate.class, callbacks -> event -> {
        for (AnvilUpdate callback : callbacks) {
            callback.onAnvilUpdate(event);
        }
    });

    @FunctionalInterface
    public interface AnvilUpdate {
        void onAnvilUpdate(AnvilUpdateEvent event);
    }
}