package carminite.crafting;

import carminite.Carminite;
import com.mojang.datafixers.util.Either;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import java.util.List;
import java.util.function.Function;
import java.util.stream.Stream;

import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredient;
import net.fabricmc.fabric.api.recipe.v1.ingredient.CustomIngredientSerializer;
import net.minecraft.core.Holder;
import net.minecraft.network.RegistryFriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.resources.Identifier;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.crafting.Ingredient;
import net.minecraft.world.item.crafting.display.SlotDisplay;

public record CompoundIngredient(List<Ingredient> children) implements CustomIngredient {
    public static final MapCodec<CompoundIngredient> CODEC = Codec
        .mapEither(Ingredient.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("children"), Ingredient.CODEC.listOf(1, Integer.MAX_VALUE).fieldOf("ingredients"))
        .xmap(either -> either.map(Function.identity(), Function.identity()), Either::left)
        .xmap(CompoundIngredient::new, CompoundIngredient::children);

    public static final CustomIngredientSerializer<CompoundIngredient> SERIALIZER = new CustomIngredientSerializer<>() {
        @Override
        public Identifier getIdentifier() {
            return Carminite.id("compound");
        }

        @Override
        public MapCodec<CompoundIngredient> getCodec() {
            return CODEC;
        }

        @Override
        public StreamCodec<RegistryFriendlyByteBuf, CompoundIngredient> getStreamCodec() {
            return Ingredient.CONTENTS_STREAM_CODEC.apply(ByteBufCodecs.list()).map(CompoundIngredient::new, CompoundIngredient::children);
        }
    };

    public CompoundIngredient {
        if (children.isEmpty()) {
            throw new IllegalArgumentException("Compound ingredient must have at least one child.");
        }
    }

    public static Ingredient of(Ingredient... children) {
        return children.length == 1 ? children[0] : (new CompoundIngredient(List.of(children))).toVanilla();
    }

    public Stream<Holder<Item>> items() {
        return this.children.stream().flatMap(Ingredient::items);
    }

    @Override
    public boolean requiresTesting() {
        return true;
    }

    @Override
    public CustomIngredientSerializer<?> getSerializer() {
        return SERIALIZER;
    }

    public boolean test(ItemStack stack) {
        for(Ingredient child : this.children) {
            if (child.test(stack)) {
                return true;
            }
        }
        return false;
    }

    public SlotDisplay display() {
        return new SlotDisplay.Composite(this.children.stream().map(Ingredient::display).toList());
    }
}