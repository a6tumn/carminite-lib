package carminite.events.api;

import carminite.events.neoforge.BreakBlockEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class BlockEvents {
    public static final Event<BreakBlock> BREAK_BLOCK = EventFactory.createArrayBacked(BreakBlock.class, callbacks -> event -> {
        for (BreakBlock callback : callbacks) {
            callback.fireBlockBreak(event);
        }
    });

    @FunctionalInterface
    public interface BreakBlock {
        void fireBlockBreak(BreakBlockEvent event);
    }
}