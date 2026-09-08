package carminite.events.neoforge;

import carminite.events.ICancellableEvent;
import carminite.events.api.LivingEvents;
import net.minecraft.world.entity.LivingEntity;

public class LivingFallEvent extends LivingEvent implements ICancellableEvent {
    private double distance;
    private float damageMultiplier;

    public LivingFallEvent(LivingEntity entity, double distance, float damageMultiplier) {
        super(entity);
        this.setDistance(distance);
        this.setDamageMultiplier(damageMultiplier);
    }

    public double getDistance() {
        return distance;
    }

    public void setDistance(double distance) {
        this.distance = distance;
    }

    public float getDamageMultiplier() {
        return damageMultiplier;
    }

    public void setDamageMultiplier(float damageMultiplier) {
        this.damageMultiplier = damageMultiplier;
    }

    @Override
    public LivingFallEvent post() {
        LivingEvents.LIVING_FALL.invoker().onLivingFall(this);
        return this;
    }
}