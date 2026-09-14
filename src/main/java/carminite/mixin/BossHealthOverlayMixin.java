package carminite.mixin;

import carminite.events.hooks.ClientHooks;
import carminite.events.neoforge.CustomizeGuiOverlayEvent;
import com.llamalad7.mixinextras.injector.v2.WrapWithCondition;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalRef;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphicsExtractor;
import net.minecraft.client.gui.components.BossHealthOverlay;
import net.minecraft.client.gui.components.LerpingBossEvent;
import net.minecraft.network.chat.Component;
import net.minecraft.world.BossEvent;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(BossHealthOverlay.class)
public class BossHealthOverlayMixin {

    @Shadow
    @Final
    private Minecraft minecraft;

    @WrapOperation(
        method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/components/BossHealthOverlay;extractBar(Lnet/minecraft/client/gui/GuiGraphicsExtractor;IILnet/minecraft/world/BossEvent;)V"
        )
    )
    private void carminite$customizeBossEvent(
        BossHealthOverlay instance,
        GuiGraphicsExtractor graphics,
        int x,
        int y,
        BossEvent event,
        Operation<Void> original,
        @Local(name = "event") LerpingBossEvent lerpingBossEvent,
        @Share(value = "bossEvent", namespace = "carminite") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> bossEvent
    ) {
        CustomizeGuiOverlayEvent.BossEventProgress customizeEvent = ClientHooks.onCustomizeBossEventProgress(graphics, this.minecraft.getWindow(), lerpingBossEvent, x, y, 10 + this.minecraft.font.lineHeight);
        bossEvent.set(customizeEvent);

        if (!customizeEvent.isCanceled()) {
            original.call(instance, graphics, x, y, event);
        }
    }

    @WrapWithCondition(
        method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/gui/GuiGraphicsExtractor;text(Lnet/minecraft/client/gui/Font;Lnet/minecraft/network/chat/Component;III)V"
        )
    )
    private boolean carminite$customizeBossName(
        GuiGraphicsExtractor instance,
        Font font,
        Component str,
        int x,
        int y,
        int color,
        @Share(value = "bossEvent", namespace = "carminite") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> bossEvent
    ) {
        CustomizeGuiOverlayEvent.BossEventProgress event = bossEvent.get();
        return event == null || !event.isCanceled();
    }

    @ModifyVariable(
        method = "extractRenderState(Lnet/minecraft/client/gui/GuiGraphicsExtractor;)V",
        at = @At(
            value = "STORE",
            ordinal = 1
        ),
        name = "yOffset"
    )
    private int carminite$customIncrement(
        int yOffset,
        @Share(value = "bossEvent", namespace = "carminite") LocalRef<CustomizeGuiOverlayEvent.BossEventProgress> bossEvent
    ) {
        var event = bossEvent.get();
        if (event == null) {
            return yOffset;
        }

        int oldYOffset = yOffset - 19;
        if (event.isCanceled()) {
            return oldYOffset;
        }

        return oldYOffset + event.getIncrement();
    }
}