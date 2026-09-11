package carminite.mixin;

import carminite.events.hooks.CarminiteHooks;
import carminite.events.modified.CarminiteEntityTeleportEvent;
import net.minecraft.core.Direction;
import net.minecraft.world.entity.monster.Shulker;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Shulker.class)
public class ShulkerMixin {

    @ModifyVariable(
        method = "teleportSomewhere()Z",
        at = @At("STORE"),
        name = "attachmentDirection"
    )
    private Direction carminite$entityTeleport(Direction attachmentDirection) {
        CarminiteEntityTeleportEvent event = CarminiteHooks.onEntityTeleport((Shulker) (Object) this);
        if (event.isCanceled()) {
            return null;
        }

        return attachmentDirection;
    }
}