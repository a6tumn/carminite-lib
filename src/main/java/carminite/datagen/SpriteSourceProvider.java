package carminite.datagen;

import carminite.conditions.ConditionalOps;
import carminite.conditions.ICondition;
import carminite.conditions.WithConditions;
import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.concurrent.CompletableFuture;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class SpriteSourceProvider extends JsonCodecProvider<List<Optional<WithConditions<SpriteSource>>>> {
    private static final Codec<List<Optional<WithConditions<SpriteSource>>>> CODEC = ConditionalOps.createConditionalCodecWithConditions(SpriteSources.CODEC)
        .listOf()
        .fieldOf("sources")
        .codec();

    private final Map<Identifier, SourceList> atlases = new HashMap<>();

    public SpriteSourceProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, String modId) {
        super(output, PackOutput.Target.RESOURCE_PACK, "atlases", CODEC, lookupProvider, modId);
    }

    protected final SourceList atlas(Identifier id) {
        return atlases.computeIfAbsent(id, i -> {
            SourceList newAtlas = new SourceList();
            unconditional(i, newAtlas.sources);
            return newAtlas;
        });
    }

    protected static final class SourceList {
        private final List<Optional<WithConditions<SpriteSource>>> sources = new ArrayList<>();

        private SourceList() {}

        public SourceList addSource(SpriteSource source) {
            sources.add(Optional.of(new WithConditions<>(source)));
            return this;
        }

        public SourceList addSource(SpriteSource source, ICondition... conditions) {
            sources.add(Optional.of(new WithConditions<>(source, conditions)));
            return this;
        }
    }
}