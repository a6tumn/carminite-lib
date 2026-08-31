package carminite.mixin;

import carminite.events.hooks.EventHooks;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.projectile.arrow.AbstractArrow;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.EntityHitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(AbstractArrow.class)
public class AbstractArrowMixin {

    @Inject(
        method = "stepMoveAndHit(Lnet/minecraft/world/phys/BlockHitResult;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
        ),
        cancellable = true
    )
    private void carminite$projectileImpactBlock(
        BlockHitResult blockHitResult,
        CallbackInfo ci
    ) {
        if (EventHooks.onProjectileImpact((AbstractArrow) (Object) this, blockHitResult)) {
            ci.cancel();
        }
    }

    @Inject(
        method = "stepMoveAndHit(Lnet/minecraft/world/phys/BlockHitResult;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/projectile/arrow/AbstractArrow;hitTargetsOrDeflectSelf(Ljava/util/Collection;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
        ),
        cancellable = true
    )
    private void carminite$projectileImpactEntities(
        BlockHitResult blockHitResult,
        CallbackInfo ci,
        @Local(name = "firstEntityHit") EntityHitResult firstEntityHit
    ) {
        if (EventHooks.onProjectileImpact((AbstractArrow) (Object) this, firstEntityHit)) {
            ci.cancel();
        }
    }
}