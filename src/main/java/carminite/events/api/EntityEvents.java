package carminite.events.api;

import carminite.events.neoforge.EntityJoinLevelEvent;
import carminite.events.neoforge.EntityStruckByLightningEvent;
import carminite.events.neoforge.ProjectileImpactEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class EntityEvents {
    public static final Event<LightningStruck> ENTITY_STRUCK_BY_LIGHTNING = EventFactory.createArrayBacked(LightningStruck.class, callbacks -> event -> {
        for (LightningStruck callback : callbacks) {
            callback.onEntityStruckByLightning(event);
        }
    });

    public static final Event<ProjectileImpact> PROJECTILE_IMPACT = EventFactory.createArrayBacked(ProjectileImpact.class, callbacks -> event -> {
        for (ProjectileImpact callback : callbacks) {
            callback.onProjectileImpact(event);
        }
    });

    public static final Event<JoinLevel> JOIN_LEVEL = EventFactory.createArrayBacked(JoinLevel.class, callbacks -> event -> {
        for (JoinLevel callback : callbacks) {
            callback.onEntityJoinLevel(event);
        }
    });

    @FunctionalInterface
    public interface LightningStruck {
        void onEntityStruckByLightning(EntityStruckByLightningEvent event);
    }

    @FunctionalInterface
    public interface ProjectileImpact {
        void onProjectileImpact(ProjectileImpactEvent event);
    }

    @FunctionalInterface
    public interface JoinLevel {
        void onEntityJoinLevel(EntityJoinLevelEvent event);
    }
}