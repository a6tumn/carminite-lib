package carminite.mixin;

import carminite.events.hooks.CommonHooks;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.server.level.ServerPlayerGameMode;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ServerPlayerGameMode.class)
public class ServerPlayerGameModeMixin {

	@Shadow
	protected ServerLevel level;

	@Shadow
	private GameType gameModeForPlayer;

	@Shadow
	@Final
	protected ServerPlayer player;

	@ModifyExpressionValue(
		method = "destroyBlock(Lnet/minecraft/core/BlockPos;)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/item/ItemStack;canDestroyBlock(Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/Level;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/entity/player/Player;)Z"
		)
	)
	private boolean carminite$fireBreakBlockEvent(
		boolean original,
		@Local(argsOnly = true, name = "pos") BlockPos pos,
		@Local(name = "state") BlockState state
	) {
		var event = CommonHooks.fireBlockBreak(this.level, this.gameModeForPlayer, this.player, pos, state);
		return !event.isCanceled();
	}

	@Inject(
		method = "useItemOn(Lnet/minecraft/server/level/ServerPlayer;Lnet/minecraft/world/level/Level;Lnet/minecraft/world/item/ItemStack;Lnet/minecraft/world/InteractionHand;Lnet/minecraft/world/phys/BlockHitResult;)Lnet/minecraft/world/InteractionResult;",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/world/level/block/state/BlockState;getBlock()Lnet/minecraft/world/level/block/Block;",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void carminite$onRightClickBlock(
		ServerPlayer player,
		Level level,
		ItemStack itemStack,
		InteractionHand hand,
		BlockHitResult hitResult,
		CallbackInfoReturnable<InteractionResult> cir,
		@Local(name = "pos") BlockPos pos
	) {
		var event = CommonHooks.onRightClickBlock(player, hand, pos, hitResult);
		if (event.isCanceled()) {
			cir.setReturnValue(event.getCancellationResult());
		}
	}
}