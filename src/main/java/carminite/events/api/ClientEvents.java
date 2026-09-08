package carminite.events.api;

import carminite.events.modified.CarminiteComputeFogColorEvent;
import carminite.events.neoforge.*;
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

    public static final Event<ComputeFovModifier> COMPUTE_FOV_MODIFIER = EventFactory.createArrayBacked(ComputeFovModifier.class, callbacks -> event -> {
        for (ComputeFovModifier callback : callbacks) {
            callback.getFieldOfViewModifier(event);
        }
    });

    public static final Event<CalculatePlayerTurn> CALCULATE_PLAYER_TURN = EventFactory.createArrayBacked(CalculatePlayerTurn.class, callbacks -> event -> {
        for (CalculatePlayerTurn callback : callbacks) {
            callback.getTurnPlayerValues(event);
        }
    });

    public static final Event<ComputeFogColor> CARMINITE_COMPUTE_FOG_COLOR = EventFactory.createArrayBacked(ComputeFogColor.class, callbacks -> event -> {
        for (ComputeFogColor callback : callbacks) {
            callback.getFogColor(event);
        }
    });

    public static final Event<SelectMusic> SELECT_MUSIC = EventFactory.createArrayBacked(SelectMusic.class, callbacks -> event -> {
        for (SelectMusic callback : callbacks) {
            callback.selectMusic(event);
        }
    });

    public static final Event<CustomizeBossHealth> CUSTOMIZE_BOSS_HEALTH_OVERLAY = EventFactory.createArrayBacked(CustomizeBossHealth.class, callbacks -> event -> {
        for (CustomizeBossHealth callback : callbacks) {
            callback.onCustomizeBossEventProgress(event);
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

    @FunctionalInterface
    public interface ComputeFovModifier {
        void getFieldOfViewModifier(ComputeFovModifierEvent event);
    }

    @FunctionalInterface
    public interface CalculatePlayerTurn {
        void getTurnPlayerValues(CalculatePlayerTurnEvent event);
    }

    @FunctionalInterface
    public interface ComputeFogColor {
        void getFogColor(CarminiteComputeFogColorEvent event);
    }

    @FunctionalInterface
    public interface SelectMusic {
        void selectMusic(SelectMusicEvent event);
    }

    @FunctionalInterface
    public interface CustomizeBossHealth {
        void onCustomizeBossEventProgress(CustomizeGuiOverlayEvent.BossEventProgress event);
    }
}