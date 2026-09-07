package carminite.events.neoforge;

import carminite.events.CarminiteEvent;
import carminite.events.api.ClientEvents;
import net.minecraft.util.Mth;
import net.minecraft.world.entity.player.Player;
import org.jetbrains.annotations.ApiStatus;

public class ComputeFovModifierEvent extends CarminiteEvent {
    private final Player player;
    private final float fovModifier;
    private final float fovScale;
    private float newFovModifier;

    @ApiStatus.Internal
    public ComputeFovModifierEvent(Player player, float fovModifier, float fovScale) {
        this.player = player;
        this.fovModifier = fovModifier;
        this.fovScale = fovScale;
        this.setNewFovModifier(Mth.lerp(fovScale, 1.0F, fovModifier));
    }

    public Player getPlayer() {
        return player;
    }

    public float getFovModifier() {
        return fovModifier;
    }

    public float getFovScale() {
        return fovScale;
    }

    public float getNewFovModifier() {
        return newFovModifier;
    }

    public void setNewFovModifier(float newFovModifier) {
        this.newFovModifier = newFovModifier;
    }

    @Override
    public ComputeFovModifierEvent post() {
        ClientEvents.COMPUTE_FOV_MODIFIER.invoker().getFieldOfViewModifier(this);
        return this;
    }
}