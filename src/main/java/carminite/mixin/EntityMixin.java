package carminite.mixin;

import carminite.events.hooks.EventHooks;
import carminite.interfaces.extensions.IEntityExtension;
import carminite.interfaces.markers.ISpecialRunningEffectsBlock;
import com.llamalad7.mixinextras.expression.Definition;
import com.llamalad7.mixinextras.expression.Expression;import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.Vec3;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Entity.class)
public class EntityMixin {

	@Shadow
	private Level level;

	@Definition(
		id = "blockState",
		local = @Local(type = BlockState.class)
	)
	@Definition(
		id = "getRenderShape",
		method = "Lnet/minecraft/world/level/block/state/BlockState;getRenderShape()Lnet/minecraft/world/level/block/RenderShape;"
	)
	@Definition(
		id = "INVISIBLE",
		field = "Lnet/minecraft/world/level/block/RenderShape;INVISIBLE:Lnet/minecraft/world/level/block/RenderShape;"
	)
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
		method = "interact(Lnet/minecraft/world/entity/player/Player;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/Vec3;)Lnet/minecraft/world/InteractionResult;",
		at = @At("HEAD"),
		cancellable = true
	)
	private void carminite$allowRiderInteract(
		Player player,
		InteractionHand hand,
		Vec3 location,
		CallbackInfoReturnable<InteractionResult> cir
	) {
		Entity self = (Entity) (Object) this;
		if (self.isVehicle() && self.hasPassenger(player) && ((IEntityExtension) (Object) this).canRiderInteract()) {
			cir.setReturnValue(self.level().isClientSide() ? InteractionResult.SUCCESS : InteractionResult.SUCCESS_SERVER);
		}
	}
}