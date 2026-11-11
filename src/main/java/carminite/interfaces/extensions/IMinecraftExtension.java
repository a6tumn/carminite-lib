package carminite.interfaces.extensions;

import net.minecraft.client.renderer.block.BlockModelResolver;

public interface IMinecraftExtension {
	default BlockModelResolver carminite$getBlockModelResolver() {
		throw new AssertionError("Implemented via mixin");
	}
}
