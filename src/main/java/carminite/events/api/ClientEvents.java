package carminite.events.api;

import carminite.events.neoforge.InputEvent;
import carminite.events.neoforge.MovementInputUpdateEvent;
import carminite.events.neoforge.RenderFrameEvent;
import carminite.events.neoforge.ViewportEvent;
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

    public static final Event<ComputeCameraAngles> COMPUTE_CAMERA_ANGLES = EventFactory.createArrayBacked(ComputeCameraAngles.class, callbacks -> event -> {
        for (ComputeCameraAngles callback : callbacks) {
            callback.computeCameraAngles(event);
        }
    });

    public static final Event<MovementInputUpdate> MOVEMENT_INPUT_UPDATE = EventFactory.createArrayBacked(MovementInputUpdate.class, callbacks -> event -> {
        for (MovementInputUpdate callback : callbacks) {
            callback.onMovementInputUpdate(event);
        }
    });

    public static final Event<InputKey> INPUT_KEY = EventFactory.createArrayBacked(InputKey.class, callbacks -> event -> {
        for (InputKey callback : callbacks) {
            callback.onKeyInput(event);
        }
    });

    @FunctionalInterface
    public interface RenderFramePre {
        void fireRenderFramePre(RenderFrameEvent.Pre event);
    }

    @FunctionalInterface
    public interface RenderFramePost {
        void fireRenderFramePost(RenderFrameEvent.Post event);
    }

    @FunctionalInterface
    public interface ComputeCameraAngles {
        void computeCameraAngles(ViewportEvent.ComputeCameraAngles event);
    }

    @FunctionalInterface
    public interface MovementInputUpdate {
        void onMovementInputUpdate(MovementInputUpdateEvent event);
    }

    @FunctionalInterface
    public interface InputKey {
        void onKeyInput(InputEvent.Key event);
    }
}