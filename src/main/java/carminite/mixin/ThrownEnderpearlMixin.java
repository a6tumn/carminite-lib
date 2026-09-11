package carminite.mixin;

import carminite.events.hooks.CarminiteHooks;
import carminite.events.modified.CarminiteEntityTeleportEvent;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.network.ServerGamePacketListenerImpl;
import net.minecraft.world.entity.projectile.throwableitemprojectile.ThrownEnderpearl;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ThrownEnderpearl.class)
public class ThrownEnderpearlMixin {

    @WrapOperation(
        method = "onHit(Lnet/minecraft/world/phys/HitResult;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/server/network/ServerGamePacketListenerImpl;isAcceptingMessages()Z"
        )
    )
    private boolean carminite$entityTeleport(
        ServerGamePacketListenerImpl instance,
        Operation<Boolean> original,
        @Local(name = "player") ServerPlayer player
    ) {
        CarminiteEntityTeleportEvent event = CarminiteHooks.onEntityTeleport(player);
        if (event.isCanceled()) {
            return false;
        }

        return original.call(instance);
    }
}