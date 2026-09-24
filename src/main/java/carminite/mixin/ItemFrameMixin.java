package carminite.mixin;

import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import net.minecraft.world.entity.EntityType;
import net.minecraft.world.entity.decoration.HangingEntity;
import net.minecraft.world.entity.decoration.ItemFrame;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.MapItem;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.saveddata.maps.MapItemSavedData;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;

@Mixin(ItemFrame.class)
public abstract class ItemFrameMixin extends HangingEntity {
    protected ItemFrameMixin(EntityType<? extends HangingEntity> type, Level level) {
        super(type, level);
    }

    @ModifyExpressionValue(
        method = "removeFramedMap(Lnet/minecraft/world/item/ItemStack;)V",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/world/item/MapItem;getSavedData(Lnet/minecraft/world/level/saveddata/maps/MapId;Lnet/minecraft/world/level/Level;)Lnet/minecraft/world/level/saveddata/maps/MapItemSavedData;"
        )
    )
    private MapItemSavedData carminite$removeFramedMap(
        MapItemSavedData original,
        ItemStack itemStack
    ) {
        return MapItem.getSavedData(itemStack, this.level());
    }
}