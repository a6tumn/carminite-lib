package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.boss.wither.WitherBoss;
import net.minecraft.world.entity.monster.Monster;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(WitherBoss.class)
public abstract class WitherBossMixin extends Monster {
    protected WitherBossMixin(EntityType<? extends Monster> type, Level level) {
        super(type, level);
    }

    @ModifyExpressionValue(
        method = "customServerAiStep(Lnet/minecraft/server/level/ServerLevel;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/entity/boss/wither/WitherBoss;canDestroy(Lnet/minecraft/world/level/block/state/BlockState;)Z"
        )
    )
    private boolean carminite$canEntityDestroy(
        boolean original,
        @Local(name = "blockPos") BlockPos blockPos,
        @Local(name = "state") BlockState state
    ) {
        return state.carminite$canEntityDestroy(this.level(), blockPos, (WitherBoss) (Object) this);
    }
}