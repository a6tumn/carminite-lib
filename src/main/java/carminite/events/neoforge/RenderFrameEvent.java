package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.api.ClientEvents;
import net.minecraft.client.DeltaTracker;

public abstract class RenderFrameEvent extends CarminiteEvent {
    protected final DeltaTracker partialTick;

    protected RenderFrameEvent(DeltaTracker partialTick) {
        this.partialTick = partialTick;
    }

    public DeltaTracker getPartialTick() {
        return this.partialTick;
    }

    public static class Pre extends RenderFrameEvent {
        public Pre(DeltaTracker partialTick) {
            super(partialTick);
        }

        @Override
        public Pre post() {
            ClientEvents.RENDER_FRAME_PRE.invoker().fireRenderFramePre(this);
            return this;
        }
    }

    public static class Post extends RenderFrameEvent {
        public Post(DeltaTracker partialTick) {
            super(partialTick);
        }

        @Override
        public Post post() {
            ClientEvents.RENDER_FRAME_POST.invoker().fireRenderFramePost(this);
            return this;
        }
    }
}