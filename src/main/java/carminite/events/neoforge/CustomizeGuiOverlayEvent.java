package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.ICancellableEvent;
import carminite.events.api.ClientEvents;
import com.mojang.blaze3d.platform.Window;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.LerpingBossEvent;
import org.jetbrains.annotations.ApiStatus;

public abstract class CustomizeGuiOverlayEvent extends CarminiteEvent {
    private final Window window;
    private final GuiGraphicsExtractor guiGraphics;
    private final DeltaTracker partialTick;

    @ApiStatus.Internal
    protected CustomizeGuiOverlayEvent(Window window, GuiGraphicsExtractor guiGraphics, DeltaTracker partialTick) {
        this.window = window;
        this.guiGraphics = guiGraphics;
        this.partialTick = partialTick;
    }

    public Window getWindow() {
        return window;
    }

    public GuiGraphicsExtractor getGuiGraphics() {
        return guiGraphics;
    }

    public DeltaTracker getPartialTick() {
        return partialTick;
    }

    public static class BossEventProgress extends CustomizeGuiOverlayEvent implements ICancellableEvent {
        private final LerpingBossEvent bossEvent;
        private final int x;
        private final int y;
        private int increment;

        @ApiStatus.Internal
        public BossEventProgress(Window window, GuiGraphicsExtractor guiGraphics, DeltaTracker partialTick, LerpingBossEvent bossEvent, int x, int y, int increment) {
            super(window, guiGraphics, partialTick);
            this.bossEvent = bossEvent;
            this.x = x;
            this.y = y;
            this.increment = increment;
        }

        public LerpingBossEvent getBossEvent() {
            return bossEvent;
        }

        public int getX() {
            return x;
        }

        public int getY() {
            return y;
        }

        public int getIncrement() {
            return increment;
        }

        public void setIncrement(int increment) {
            this.increment = increment;
        }

        @Override
        public BossEventProgress post() {
            ClientEvents.CUSTOMIZE_BOSS_HEALTH_OVERLAY.invoker().onCustomizeBossEventProgress(this);
            return this;
        }
    }
}