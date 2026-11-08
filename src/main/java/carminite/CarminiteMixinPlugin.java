package carminite;

import net.fabricmc.loader.api.FabricLoader;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;

import java.util.Set;

public class CarminiteMixinPlugin implements IMixinConfigPlugin {
	private static final Set<String> DISABLED_WITH_SODIUM = Set.of(
		"carminite.mixin.LevelRendererMixinExtension",
		"carminite.mixin.BlockEntityRenderDispatcherMixin"
	);

	@Override
	public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
		if (FabricLoader.getInstance().isModLoaded("sodium") && DISABLED_WITH_SODIUM.contains(mixinClassName)) {
			if (FabricLoader.getInstance().isDevelopmentEnvironment()) {
				Carminite.LOGGER.warn("Disabling {} because Sodium is present!", mixinClassName);
			}
			return false;
		}

		return IMixinConfigPlugin.super.shouldApplyMixin(targetClassName, mixinClassName);
	}
}
