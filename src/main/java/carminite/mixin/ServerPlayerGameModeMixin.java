package carminite.mixin;

import carminite.events.hooks.CommonHooks;
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

	@Inject(
		method = "destroyBlock(Lnet/minecraft/core/BlockPos;)Z",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/server/level/ServerLevel;getBlockState(Lnet/minecraft/core/BlockPos;)Lnet/minecraft/world/level/block/state/BlockState;",
			shift = At.Shift.AFTER
		),
		cancellable = true
	)
	private void carminite$fireBreakBlockEvent(
		BlockPos pos,
		CallbackInfoReturnable<Boolean> cir
	) {
		BlockState state = this.level.getBlockState(pos);
		var event = CommonHooks.fireBlockBreak(this.level, this.gameModeForPlayer, this.player, pos, state);
		if (event.isCanceled()) {
			cir.setReturnValue(false);
		}
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