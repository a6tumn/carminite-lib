package carminite.events.api;

import carminite.events.neoforge.LivingDeathEvent;
import carminite.events.neoforge.LivingEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class LivingEvents {
    public static final Event<LivingJump> LIVING_JUMP = EventFactory.createArrayBacked(LivingJump.class, callbacks -> event -> {
        for (LivingJump callback : callbacks) {
            callback.onLivingJump(event);
        }
    });

    public static final Event<LivingDeath> LIVING_DEATH = EventFactory.createArrayBacked(LivingDeath.class, callbacks -> event -> {
        for (LivingDeath callback : callbacks) {
            callback.onLivingDeath(event);
        }
    });

    @FunctionalInterface
    public interface LivingJump {
        void onLivingJump(LivingEvent.LivingJumpEvent event);
    }

    @FunctionalInterface
    public interface LivingDeath {
        void onLivingDeath(LivingDeathEvent event);
    }
}