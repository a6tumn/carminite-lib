package carminite.util;

import net.minecraft.client.Minecraft;
import net.minecraft.world.item.TooltipFlag;
import org.jetbrains.annotations.ApiStatus;

public record ClientTooltipFlag(boolean advanced, boolean creative, boolean shiftDown, boolean controlDown, boolean altDown) implements TooltipFlag {

    @ApiStatus.Internal
    public ClientTooltipFlag {
    }

    public boolean isAdvanced() {
        return this.advanced;
    }

    public boolean isCreative() {
        return this.creative;
    }

    public boolean hasControlDown() {
        return this.controlDown;
    }

    public boolean hasShiftDown() {
        return this.shiftDown;
    }

    public boolean hasAltDown() {
        return this.altDown;
    }

    public static TooltipFlag of(TooltipFlag other) {
        Minecraft mc = Minecraft.getInstance();
        return new ClientTooltipFlag(other.isAdvanced(), other.isCreative(), mc.hasShiftDown(), mc.hasControlDown(), mc.hasAltDown());
    }
}