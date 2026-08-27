package carminite.mixin;

import carminite.interfaces.extensions.IEntityExtension;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.projectile.ProjectileUtil;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ProjectileUtil.class)
public class ProjectileUtilMixin {

	@Redirect(
		method = "getEntityHitResult(Lnet/minecraft/world/entity/Entity;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/Vec3;Lnet/minecraft/world/phys/AABB;Ljava/util/function/Predicate;D)Lnet/minecraft/world/phys/EntityHitResult;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;getRootVehicle()Lnet/minecraft/world/entity/Entity;",
			ordinal = 0
		)
	)
	private static Entity carminite$skipRiderTarget(Entity entity) {
		return ((IEntityExtension) entity).carminite$canRiderInteract()
			? null
			: entity.getRootVehicle();
	}
}