package carminite.datagen;

import com.mojang.serialization.Codec;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.concurrent.CompletableFuture;
import java.util.function.BiConsumer;

import net.fabricmc.fabric.api.datagen.v1.FabricPackOutput;
import net.fabricmc.fabric.api.datagen.v1.provider.FabricCodecDataProvider;
import net.minecraft.client.renderer.texture.atlas.SpriteSource;
import net.minecraft.client.renderer.texture.atlas.SpriteSources;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.Identifier;

public abstract class SpriteSourceProvider extends FabricCodecDataProvider<List<SpriteSource>> {
    private static final Codec<List<SpriteSource>> CODEC = SpriteSources.CODEC
        .listOf()
        .fieldOf("sources")
        .codec();

    private final Map<Identifier, SourceList> atlases = new HashMap<>();

    public SpriteSourceProvider(FabricPackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider) {
        super(output, lookupProvider, PackOutput.Target.RESOURCE_PACK, "atlases", CODEC);
    }

    @Override
    protected final void configure(BiConsumer<Identifier, List<SpriteSource>> provider, HolderLookup.Provider registryLookup) {
        gather();
        atlases.forEach((id, sourceList) ->
            provider.accept(id, sourceList.sources)
        );
    }

    protected abstract void gather();

    protected final SourceList atlas(Identifier id) {
        return atlases.computeIfAbsent(id, _ -> new SourceList());
    }

    protected static final class SourceList {
        private final List<SpriteSource> sources = new ArrayList<>();

        private SourceList() {}

        public SourceList addSource(SpriteSource source) {
            sources.add(source);
            return this;
        }
    }
}