package carminite.events.api;

import carminite.events.neoforge.EntityTickEvent;
import carminite.events.neoforge.PlayerTickEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class TickEvents {
    public static final Event<EntityTickPre> ENTITY_TICK_PRE = EventFactory.createArrayBacked(EntityTickPre.class, callbacks -> event -> {
        for (EntityTickPre callback : callbacks) {
            callback.fireEntityTickPre(event);
        }
    });

    public static final Event<EntityTickPost> ENTITY_TICK_POST = EventFactory.createArrayBacked(EntityTickPost.class, callbacks -> event -> {
        for (EntityTickPost callback : callbacks) {
            callback.fireEntityTickPost(event);
        }
    });

    public static final Event<PlayerTickPre> PLAYER_TICK_PRE = EventFactory.createArrayBacked(PlayerTickPre.class, callbacks -> event -> {
        for (PlayerTickPre callback : callbacks) {
            callback.firePlayerTickPre(event);
        }
    });

    public static final Event<PlayerTickPost> PLAYER_TICK_POST = EventFactory.createArrayBacked(PlayerTickPost.class, callbacks -> event -> {
        for (PlayerTickPost callback : callbacks) {
            callback.firePlayerTickPost(event);
        }
    });

    @FunctionalInterface
    public interface EntityTickPre {
        void fireEntityTickPre(EntityTickEvent.Pre event);
    }

    @FunctionalInterface
    public interface EntityTickPost {
        void fireEntityTickPost(EntityTickEvent.Post event);
    }

    @FunctionalInterface
    public interface PlayerTickPre {
        void firePlayerTickPre(PlayerTickEvent.Pre event);
    }

    @FunctionalInterface
    public interface PlayerTickPost {
        void firePlayerTickPost(PlayerTickEvent.Post event);
    }
}