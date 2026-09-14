package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.entity.ai.goal.RemoveBlockGoal;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.chunk.ChunkAccess;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(RemoveBlockGoal.class)
public class RemoveBlockGoalMixin {

    @Shadow
    @Final
    private Mob removerMob;

    @ModifyExpressionValue(
        method = "isValidTarget(Lnet/minecraft/world/level/LevelReader;Lnet/minecraft/core/BlockPos;)Z",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/block/state/BlockState;is(Ljava/lang/Object;)Z"
        )
    )
    private boolean carminite$canEntityDestroy(
        boolean original,
        @Local(argsOnly = true, name = "level") LevelReader level,
        @Local(argsOnly = true, name = "pos") BlockPos pos,
        @Local(name = "chunk") ChunkAccess chunk
    ) {
        return original &&  chunk.getBlockState(pos).carminite$canEntityDestroy(level, pos, this.removerMob);
    }
}