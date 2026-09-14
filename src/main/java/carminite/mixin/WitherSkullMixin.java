package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.projectile.hurtingprojectile.WitherSkull;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherSkull.class)
public class WitherSkullMixin {

    @ModifyExpressionValue(
        method = "getBlockExplosionResistance(Lnet/minecraft/world/level/Explosion;Lnet/minecraft/world/level/BlockGetter;Lnet/minecraft/core/BlockPos;Lnet/minecraft/world/level/block/state/BlockState;Lnet/minecraft/world/level/material/FluidState;F)F",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;canDestroy(Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean carminite$canEntityDestroy(
        boolean original,
        @Local(argsOnly = true, name = "level") BlockGetter level,
        @Local(argsOnly = true, name = "pos") BlockPos pos,
        @Local(argsOnly = true, name = "block") BlockState block
    ) {
        return block.carminite$canEntityDestroy(level, pos, (WitherSkull) (Object) this);
    }
}