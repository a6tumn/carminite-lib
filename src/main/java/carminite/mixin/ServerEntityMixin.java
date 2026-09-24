package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.server.level.ServerEntity;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ServerEntity.class)
public class ServerEntityMixin {

    @Shadow
    @Final
    private ServerLevel level;

    @ModifyExpressionValue(
        method = "sendChanges()V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/MapItem;getSavedData(Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;"
        )
    )
    private MapItemSavedData carminite$sendChanges(
        MapItemSavedData original,
        @Local(name = "itemStack") ItemStack itemStack
    ) {
        return MapItem.getSavedData(itemStack, this.level);
    }
}