package carminite.events.api;

import carminite.events.neoforge.RenderFrameEvent;
import net.fabricmc.fabric.api.event.Event;
import net.fabricmc.fabric.api.event.EventFactory;

public final class ClientEvents {
    public static final Event<RenderFramePre> RENDER_FRAME_PRE = EventFactory.createArrayBacked(RenderFramePre.class, callbacks -> event -> {
        for (RenderFramePre callback : callbacks) {
            callback.fireRenderFramePre(event);
        }
    });

    public static final Event<RenderFramePost> RENDER_FRAME_POST = EventFactory.createArrayBacked(RenderFramePost.class, callbacks -> event -> {
        for (RenderFramePost callback : callbacks) {
            callback.fireRenderFramePost(event);
        }
    });

    public interface RenderFramePre {
        void fireRenderFramePre(RenderFrameEvent.Pre event);
    }

    public interface RenderFramePost {
        void fireRenderFramePost(RenderFrameEvent.Post event);
    }
}