package carminite.events.neoforge;

import carminite.events.ICancellableEvent;
import carminite.events.api.LivingEvents;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import org.jetbrains.annotations.ApiStatus;

import java.util.EnumMap;
import java.util.Map;

public class ArmorHurtEvent extends LivingEvent implements ICancellableEvent {
    public static class ArmorEntry {
        public ItemStack armorItemStack;
        public final float originalDamage;
        public float newDamage;

        public ArmorEntry(ItemStack armorStack, float damageIn) {
            this.armorItemStack = armorStack;
            this.originalDamage = damageIn;
            this.newDamage = damageIn;
        }
    }

    private final DamageSource source;
    private final EnumMap<EquipmentSlot, ArmorEntry> armorEntries;

    @ApiStatus.Internal
    public ArmorHurtEvent(EnumMap<EquipmentSlot, ArmorEntry> armorMap, LivingEntity player, DamageSource source) {
        super(player);
        this.armorEntries = armorMap;
        this.source = source;
    }

    public ItemStack getArmorItemStack(EquipmentSlot slot) {
        return armorEntries.containsKey(slot) ? armorEntries.get(slot).armorItemStack : ItemStack.EMPTY;
    }

    public Float getOriginalDamage(EquipmentSlot slot) {
        return armorEntries.containsKey(slot) ? armorEntries.get(slot).originalDamage : 0f;
    }

    public Float getNewDamage(EquipmentSlot slot) {
        return armorEntries.containsKey(slot) ? armorEntries.get(slot).newDamage : 0f;
    }

    public void setNewDamage(EquipmentSlot slot, float damage) {
        if (this.armorEntries.containsKey(slot)) this.armorEntries.get(slot).newDamage = damage;
    }

    public Map<EquipmentSlot, ArmorEntry> getArmorMap() {
        return armorEntries;
    }

    public DamageSource getDamageSource() {
        return source;
    }

    @Override
    public ArmorHurtEvent post() {
        LivingEvents.ARMOR_HURT.invoker().onArmorHurt(this);
        return this;
    }
}