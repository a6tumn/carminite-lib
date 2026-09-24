package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.renderer.entity.ItemFrameRenderer;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFrameRenderer.class)
public class ItemFrameRendererMixin {

    @ModifyExpressionValue(
        method = "extractRenderState(Lnet/minecraft/world/entity/decoration/ItemFrame;Lnet/minecraft/client/renderer/entity/state/ItemFrameRenderState;F)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/level/Level;getMapData(Lnet/minecraft/world/level/saveddata/maps/MapId;)Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;"
        )
    )
    private <T extends ItemFrame> MapItemSavedData carminite$extractRenderState(
        MapItemSavedData original,
        @Local(name = "entity", argsOnly = true) T entity,
        @Local(name = "itemStack") ItemStack itemStack
    ) {
        return MapItem.getSavedData(itemStack, entity.level());
    }
}