package carminite.conditions;

import carminite.Carminite;
import com.mojang.serialization.MapCodec;
import net.minecraft.core.Registry;

public class CarminiteConditions {
    public static final MapCodec<AndCondition> AND_CONDITION = register("and", AndCondition.CODEC);
    public static final MapCodec<NeverCondition> NEVER_CONDITION = register("never", NeverCondition.CODEC);
    public static final MapCodec<RegisteredCondition<?>> REGISTERED_CONDITION = register("registered", RegisteredCondition.CODEC);
    public static final MapCodec<ModLoadedCondition> MOD_LOADED_CONDITION = register("mod_loaded", ModLoadedCondition.CODEC);
    public static final MapCodec<NotCondition> NOT_CONDITION = register("not", NotCondition.CODEC);
    public static final MapCodec<OrCondition> OR_CONDITION = register("or", OrCondition.CODEC);
    public static final MapCodec<TagEmptyCondition<?>> TAG_EMPTY_CONDITION = register("tag_empty", TagEmptyCondition.CODEC);
    public static final MapCodec<AlwaysCondition> ALWAYS_CONDITION = register("always", AlwaysCondition.CODEC);
    public static final MapCodec<FeatureFlagsEnabledCondition> FEATURE_FLAGS_ENABLED_CONDITION = register("feature_flags_enabled", FeatureFlagsEnabledCondition.CODEC);

    private static <T extends ICondition> MapCodec<T> register(String name, MapCodec<T> codec) {
        return Registry.register(
            Carminite.CONDITION_SERIALIZERS,
            Carminite.id(name),
            codec
        );
    }

    public static void init() {
        Carminite.LOGGER.info("Initializing conditions...");
    }
}