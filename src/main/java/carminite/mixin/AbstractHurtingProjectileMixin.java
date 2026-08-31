package carminite.mixin;

import carminite.events.hooks.EventHooks;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Share;
import com.llamalad7.mixinextras.sugar.ref.LocalBooleanRef;
import net.minecraft.world.entity.projectile.ProjectileDeflection;
import net.minecraft.world.entity.projectile.hurtingprojectile.AbstractHurtingProjectile;
import net.minecraft.world.phys.HitResult;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(AbstractHurtingProjectile.class)
public class AbstractHurtingProjectileMixin {

	@WrapOperation(
		method = "tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/phys/HitResult;getType()Lnet/minecraft/world/phys/HitResult$Type;",
			ordinal = 0
		)
	)
	private HitResult.Type carminite$projectileImpact(
		HitResult hitResult,
		Operation<HitResult.Type> original,
		@Share(value = "impacted", namespace = "carminite") LocalBooleanRef impacted
	) {
		HitResult.Type type = original.call(hitResult);

		if (type == HitResult.Type.MISS) {
			impacted.set(false);
			return type;
		}

		boolean allowed = !EventHooks.onProjectileImpact((AbstractHurtingProjectile) (Object) this, hitResult);
		impacted.set(allowed);

		return type;
	}

	@WrapOperation(
		method = "tick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/projectile/hurtingprojectile/AbstractHurtingProjectile;hitTargetOrDeflectSelf(Lnet/minecraft/world/phys/HitResult;)Lnet/minecraft/world/entity/projectile/ProjectileDeflection;"
		)
	)
	private ProjectileDeflection carminite$allowProjectileImpact(
		AbstractHurtingProjectile projectile,
		HitResult hitResult,
		Operation<ProjectileDeflection> original,
		@Share(value = "impacted", namespace = "carminite") LocalBooleanRef impacted
	) {
		if (!impacted.get()) {
			return ProjectileDeflection.NONE;
		}

		return original.call(projectile, hitResult);
	}
}