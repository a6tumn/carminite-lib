package carminite.mixin;

import carminite.events.hooks.EventHooks;
import com.llamalad7.mixinextras.injector.ModifyReturnValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @ModifyReturnValue(
        method = "mobsAt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;)Lnet/minecraft/util/random/WeightedList;",
        at = @At(
            value = "RETURN",
            ordinal = 1
        )
    )
    private static WeightedList<MobSpawnSettings.SpawnerData> carminite$potentialSpawns(
        WeightedList<MobSpawnSettings.SpawnerData> original,
        @Local(argsOnly = true, name = "level") ServerLevel level,
        @Local(argsOnly = true, name = "structureManager") StructureManager structureManager,
        @Local(argsOnly = true, name = "generator") ChunkGenerator generator,
        @Local(argsOnly = true, name = "mobCategory") MobCategory mobCategory,
        @Local(argsOnly = true, name = "pos") BlockPos pos,
        @Local(argsOnly = true, name = "biome") @Nullable Holder<Biome> biome
    ) {
        return EventHooks.getPotentialSpawns(level, mobCategory, pos, generator.getMobsAt(biome != null ? biome : level.getBiome(pos), structureManager, mobCategory, pos));
    }
}
