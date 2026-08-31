package carminite.events.neoforge;

import carminite.events.api.EntityEvents;
import carminite.events.ICancellableEvent;
import net.minecraft.world.entity.projectile.Projectile;
import net.minecraft.world.phys.HitResult;

public class ProjectileImpactEvent extends EntityEvent implements ICancellableEvent {
	private final HitResult ray;
	private final Projectile projectile;

	public ProjectileImpactEvent(Projectile projectile, HitResult ray) {
		super(projectile);
		this.ray = ray;
		this.projectile = projectile;
	}

	public HitResult getRayTraceResult() {
		return ray;
	}

	public Projectile getProjectile() {
		return projectile;
	}

	@Override
	public ProjectileImpactEvent post() {
		EntityEvents.PROJECTILE_IMPACT.invoker().onProjectileImpact(this);
		return this;
	}
}