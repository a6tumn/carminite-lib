package carminite.mixin;

import carminite.interfaces.extensions.IOwnedSpawner;
import com.mojang.datafixers.util.Either;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.SpawnerBlockEntity;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(targets = "net.minecraft.world.level.block.entity.SpawnerBlockEntity$1")
public class SpawnerBlockEntity$1Mixin implements IOwnedSpawner {

	@Unique
	private SpawnerBlockEntity carminite$owner;

	@Inject(
		method = "<init>(Lnet/minecraft/world/level/block/entity/SpawnerBlockEntity;)V",
		at = @At("TAIL")
	)
	private void carminite$captureOwner(SpawnerBlockEntity this$0, CallbackInfo ci) {
		this.carminite$owner = this$0;
	}

	@Override
	public @Nullable Either<BlockEntity, Entity> carminite$getOwner() {
		return Either.left(this.carminite$owner);
	}
}