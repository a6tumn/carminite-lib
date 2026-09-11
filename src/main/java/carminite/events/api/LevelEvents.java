package carminite.events.api;

import carminite.events.neoforge.ExplosionEvent;
import carminite.events.neoforge.ItemAttributeModifierEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class LevelEvents {
    public static final Event<Detonate> DETONATE = EventFactory.createArrayBacked(Detonate.class, callbacks -> event -> {
        for (Detonate callback : callbacks) {
            callback.onExplosionDetonate(event);
        }
    });

    public static final Event<ItemAttributeModifiers> ITEM_ATTRIBUTE_MODIFIERS = EventFactory.createArrayBacked(ItemAttributeModifiers.class, callbacks -> event -> {
        for (ItemAttributeModifiers callback : callbacks) {
            callback.computeModifiedAttributes(event);
        }
    });

    @FunctionalInterface
    public interface Detonate {
        void onExplosionDetonate(ExplosionEvent.Detonate event);
    }

    @FunctionalInterface
    public interface ItemAttributeModifiers {
        void computeModifiedAttributes(ItemAttributeModifierEvent event);
    }
}