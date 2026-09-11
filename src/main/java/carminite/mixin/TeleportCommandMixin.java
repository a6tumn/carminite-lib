package carminite.mixin;

import carminite.events.hooks.CarminiteHooks;
import carminite.events.modified.CarminiteEntityTeleportEvent;
import net.minecraft.commands.CommandSourceStack;
import net.minecraft.server.commands.LookAt;
import net.minecraft.server.commands.TeleportCommand;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.Relative;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import java.util.Set;

@Mixin(TeleportCommand.class)
public class TeleportCommandMixin {

    @Inject(
        method = "performTeleport(Lnet/minecraft/commands/CommandSourceStack;Lnet/minecraft/world/entity/Entity;Lnet/minecraft/server/level/ServerLevel;DDDLjava/util/Set;FFLnet/minecraft/server/commands/LookAt;)V",
        at = @At("HEAD"),
        cancellable = true
    )
    private static void carminite$entityTeleport(
        CommandSourceStack source,
        Entity victim,
        ServerLevel level,
        double x,
        double y,
        double z,
        Set<Relative> relatives,
        float yRot,
        float xRot,
        @Nullable LookAt lookAt, CallbackInfo ci
    ) {
        CarminiteEntityTeleportEvent event = CarminiteHooks.onEntityTeleport(victim);
        if (event.isCanceled()) {
            ci.cancel();
        }
    }
}