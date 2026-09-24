package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(ServerPlayer.class)
public abstract class ServerPlayerMixin {

	@Shadow
	public abstract Level level();

	@Inject(
		method = "die(Lnet/minecraft/world/damagesource/DamageSource;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerPlayer;gameEvent(Lnet/minecraft/core/Holder;)V",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void carminite$livingDeath(
		DamageSource source,
		CallbackInfo ci
	) {
		if (CommonHooks.onLivingDeath((LivingEntity) (Object) this, source)) {
			ci.cancel();
		}
	}

	@ModifyExpressionValue(
		method = "synchronizeSpecialItemUpdates(Lnet/minecraft/world/item/ItemStack;)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/MapItem;getSavedData(Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;"
		)
	)
	private MapItemSavedData carminite$synchronizeSpecialItemUpdates(
		MapItemSavedData original,
		@Local(argsOnly = true, name = "itemStack") ItemStack itemStack
	) {
		return MapItem.getSavedData(itemStack, this.level());
	}
}