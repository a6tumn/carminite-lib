package carminite.mixin;

import carminite.events.hooks.EventHooks;
import carminite.interfaces.markers.ISpecialRunningEffectsBlock;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.objectweb.asm.Opcodes;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Shadow
	private Level level;

	@Definition(id = "blockState", local = @Local(type = BlockState.class))
	@Definition(id = "getRenderShape", method = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;")
	@Definition(id = "INVISIBLE", field = "Lnet/minecraft/world/level/block/RenderShape;INVISIBLE:Lnet/minecraft/world/level/block/RenderShape;")
	@Expression("blockState.getRenderShape() != INVISIBLE")
	@ModifyExpressionValue(
		method = "spawnSprintParticle()V",
		at = @At("MIXINEXTRAS:EXPRESSION")
	)
	private boolean carminite$addRunningEffects(
		boolean original,
		@Local(name = "pos") BlockPos pos,
		@Local(name = "blockState") BlockState blockState
	) {
		return original
			&& !(blockState.getBlock() instanceof ISpecialRunningEffectsBlock specialRunningEffectsBlock
			&& specialRunningEffectsBlock.addRunningEffects(blockState, this.level, pos, (Entity) (Object) this));
	}

	@WrapOperation(
		method = "rideTick()V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/entity/Entity;tick()V"
		)
	)
	private void carminite$entityTick(
		Entity instance,
		Operation<Void> original
	) {
		if (!EventHooks.fireEntityTickPre(instance).isCanceled()) {
			original.call(instance);
			EventHooks.fireEntityTickPost(instance);
		}
	}

	@Inject(
		method = "startRiding(Lnet/minecraft/world/entity/Entity;ZZ)Z",
		at = @At(
			value = "JUMP",
			ordinal = 1
		),
		cancellable = true
	)
	private void carminite$canMountEntityStartRiding(
		Entity entityToRide,
		boolean force,
		boolean sendEventAndTriggers,
		CallbackInfoReturnable<Boolean> cir
	) {
		if (!EventHooks.canMountEntity((Entity) (Object) this, entityToRide, true)) {
			cir.setReturnValue(false);
		}
	}

	@Inject(
		method = "removeVehicle()V",
		at = @At(
			value = "FIELD",
			target = "Lnet/minecraft/world/entity/Entity;vehicle:Lnet/minecraft/world/entity/Entity;",
			opcode = Opcodes.PUTFIELD
		),
		cancellable = true
	)
	private void carminite$canMountEntityRemoveVehicle(
		CallbackInfo ci,
		@Local(name = "oldVehicle") Entity oldVehicle
	) {
		if (!EventHooks.canMountEntity((Entity) (Object) this, oldVehicle, false)) {
			ci.cancel();
		}
	}
}