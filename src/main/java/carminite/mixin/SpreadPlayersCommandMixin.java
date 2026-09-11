package carminite.mixin;

import carminite.events.hooks.CarminiteHooks;
import carminite.events.modified.CarminiteEntityTeleportEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import net.minecraft.server.commands.SpreadPlayersCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Set;

@Mixin(SpreadPlayersCommand.class)
public class SpreadPlayersCommandMixin {

    @WrapOperation(
        method = "setPlayerPositions(Ljava/util/Collection;Lnet/minecraft/server/level/ServerLevel;[Lnet/minecraft/server/commands/SpreadPlayersCommand$Position;IZ)D",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/Entity;teleportTo(Lnet/minecraft/server/level/ServerLevel;DDDLjava/util/Set;FFZ)Z"
        )
    )
    private static boolean carminite$entityTeleport(
        Entity instance,
        ServerLevel level,
        double x,
        double y,
        double z,
        Set<Relative> relatives,
        float newYRot,
        float newXRot,
        boolean resetCamera,
        Operation<Boolean> original
    ) {
        CarminiteEntityTeleportEvent event = CarminiteHooks.onEntityTeleport(instance);
        if (event.isCanceled()) {
            return false;
        }

        return original.call(instance, level, x, y, z, relatives, newYRot, newXRot, resetCamera);
    }
}