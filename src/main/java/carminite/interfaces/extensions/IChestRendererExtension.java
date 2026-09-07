package carminite.interfaces.extensions;

import net.minecraft.client.renderer.blockentity.state.ChestRenderState;
import net.minecraft.client.resources.model.sprite.SpriteId;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.LidBlockEntity;
import org.jspecify.annotations.Nullable;

public interface IChestRendererExtension {

    @Nullable
    default <T extends BlockEntity & LidBlockEntity> SpriteId carminite$getCustomSprite(T blockEntity, ChestRenderState renderState) {
        return null;
    }
}