package carminite.events.api;

import carminite.events.neoforge.ArmorHurtEvent;
import carminite.events.neoforge.LivingDeathEvent;
import carminite.events.neoforge.LivingEvent;
import carminite.events.neoforge.LivingFallEvent;
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

    public static final Event<ArmorHurt> ARMOR_HURT = EventFactory.createArrayBacked(ArmorHurt.class, callbacks -> event -> {
        for (ArmorHurt callback : callbacks) {
            callback.onArmorHurt(event);
        }
    });

    public static final Event<LivingFall> LIVING_FALL = EventFactory.createArrayBacked(LivingFall.class, callbacks -> event -> {
        for (LivingFall callback : callbacks) {
            callback.onLivingFall(event);
        }
    });

    @FunctionalInterface
    public interface ArmorHurt {
        void onArmorHurt(ArmorHurtEvent event);
    }

    @FunctionalInterface
    public interface LivingJump {
        void onLivingJump(LivingEvent.LivingJumpEvent event);
    }

    @FunctionalInterface
    public interface LivingDeath {
        void onLivingDeath(LivingDeathEvent event);
    }

    @FunctionalInterface
    public interface LivingFall {
        void onLivingFall(LivingFallEvent event);
    }
}