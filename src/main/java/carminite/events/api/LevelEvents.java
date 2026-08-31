package carminite.events.api;

import carminite.events.neoforge.ExplosionEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class LevelEvents {
    public static final Event<Detonate> DETONATE = EventFactory.createArrayBacked(Detonate.class, callbacks -> event -> {
        for (Detonate callback : callbacks) {
            callback.onExplosionDetonate(event);
        }
    });

    @FunctionalInterface
    public interface Detonate {
        void onExplosionDetonate(ExplosionEvent.Detonate event);
    }
}