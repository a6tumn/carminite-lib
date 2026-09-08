package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.util.profiling.Profiler;
import net.minecraft.util.profiling.ProfilerFiller;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

import java.util.Map;
import java.util.Objects;
import java.util.UUID;

@Mixin(BossHealthOverlay.class)
public abstract class BossHealthOverlayMixin {

    @Shadow
    @Final
    private Map<UUID, LerpingBossEvent> events;

    @Shadow
    protected abstract void extractBar(GuiGraphicsExtractor graphics, int x, int y, BossEvent event);

    @Shadow
    @Final
    private Minecraft minecraft;

    /**
     * @author Autumn
     * @reason NeoForge significantly changes the control flow here in a way that is difficult to reproduce with Mixin.
     */
    @Overwrite
    public void extractRenderState(final GuiGraphicsExtractor graphics) {
        if (!this.events.isEmpty()) {
            graphics.nextStratum();
            ProfilerFiller profiler = Profiler.get();
            profiler.push("bossHealth");
            int screenWidth = graphics.guiWidth();
            int yOffset = 12;

            for(LerpingBossEvent event : this.events.values()) {
                int xLeft = screenWidth / 2 - 91;
                var customizeEvent = ClientHooks.onCustomizeBossEventProgress(graphics, this.minecraft.getWindow(), event, xLeft, yOffset, 10 + this.minecraft.font.lineHeight);
                if (!customizeEvent.isCanceled()) {
                    this.extractBar(graphics, xLeft, yOffset, event);
                    Component msg = event.getName();
                    int width = this.minecraft.font.width(msg);
                    int x = screenWidth / 2 - width / 2;
                    int y = yOffset - 9;
                    graphics.text(this.minecraft.font, msg, x, y, -1);
                    Objects.requireNonNull(this.minecraft.font);
                    yOffset += customizeEvent.getIncrement();
                }
                if (yOffset >= graphics.guiHeight() / 3) {
                    break;
                }
            }

            profiler.pop();
        }
    }
}