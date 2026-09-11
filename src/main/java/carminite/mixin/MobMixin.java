package carminite.mixin;

import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.world.entity.Mob;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyVariable;

@Mixin(Mob.class)
public class MobMixin {

    @ModifyVariable(
        method = "getApproximateAttributeWith",
        at = @At("STORE"),
        name = "attributeModifiers"
    )
    private ItemAttributeModifiers carminite$modifyAttributeModifiers(
        ItemAttributeModifiers attributeModifiers,
        @Local(argsOnly = true, name = "itemStack") ItemStack itemStack
    ) {
        return itemStack.carminite$getAttributeModifiers();
    }
}