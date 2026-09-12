package carminite.mixin;

import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelLocationUtils.class)
public class ModelLocationUtilsMixin {

    @Redirect(
        method = "decorateBlockModelLocation(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$decorateBlockModelLocation(String path) {
        return Identifier.parse(path).withPrefix("block/");
    }

    @Redirect(
        method = "decorateItemModelLocation(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$decorateItemModelLocation(String path) {
        return Identifier.parse(path).withPrefix("item/");
    }
}