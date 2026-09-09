package carminite.mixin;

import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemInstance;
import net.minecraft.world.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(ItemStack.class)
public abstract class ItemStackMixin implements ItemInstance {

    @Shadow
    public abstract Item getItem();

    @Override
    public int getMaxStackSize() {
        return this.getItem().carminite$getMaxStackSize((ItemStack) (Object) this);
    }
}