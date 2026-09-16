package carminite.mixin;

import carminite.events.hooks.EventHooks;
import com.llamalad7.mixinextras.injector.wrapmethod.WrapMethod;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Holder;
import net.minecraft.core.registries.Registries;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.random.WeightedList;
import net.minecraft.world.entity.MobCategory;
import net.minecraft.world.level.NaturalSpawner;
import net.minecraft.world.level.StructureManager;
import net.minecraft.world.level.biome.Biome;
import net.minecraft.world.level.biome.MobSpawnSettings;
import net.minecraft.world.level.chunk.ChunkGenerator;
import net.minecraft.world.level.levelgen.structure.BuiltinStructures;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Mixin;

@Mixin(NaturalSpawner.class)
public class NaturalSpawnerMixin {

    @WrapMethod(method = "mobsAt(Lnet/minecraft/server/level/ServerLevel;Lnet/minecraft/world/level/StructureManager;Lnet/minecraft/world/level/chunk/ChunkGenerator;Lnet/minecraft/world/entity/MobCategory;Lnet/minecraft/core/BlockPos;Lnet/minecraft/core/Holder;)Lnet/minecraft/util/random/WeightedList;")
    private static WeightedList<MobSpawnSettings.SpawnerData> carminite$potentialSpawns(
        ServerLevel level,
        StructureManager structureManager,
        ChunkGenerator generator,
        MobCategory mobCategory,
        BlockPos pos,
        @Nullable Holder<Biome> biome,
        Operation<WeightedList<MobSpawnSettings.SpawnerData>> original
    ) {
        if (NaturalSpawner.isInNetherFortressBounds(pos, level, mobCategory, structureManager)) {
            var monsterSpawns = structureManager.registryAccess().lookupOrThrow(Registries.STRUCTURE).getValueOrThrow(BuiltinStructures.FORTRESS).spawnOverrides().get(MobCategory.MONSTER);
            if (monsterSpawns != null) {
                return EventHooks.getPotentialSpawns(level, mobCategory, pos, monsterSpawns.spawns());
            }
        }
        return EventHooks.getPotentialSpawns(level, mobCategory, pos, generator.getMobsAt(biome != null ? biome : level.getBiome(pos), structureManager, mobCategory, pos));
    }
}