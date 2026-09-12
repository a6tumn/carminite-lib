package carminite.mixin;

import net.minecraft.client.data.models.model.ModelLocationUtils;
import net.minecraft.client.data.models.model.ModelTemplates;
import net.minecraft.resources.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

@Mixin(ModelTemplates.class)
public class ModelTemplatesMixin {

    @Redirect(
        method = "create(Ljava/lang/String;[Lnet/minecraft/client/data/models/model/TextureSlot;)Lnet/minecraft/client/data/models/model/ModelTemplate;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$createOne(String path) {
        return ModelLocationUtils.decorateBlockModelLocation(path);
    }

    @Redirect(
        method = "create(Ljava/lang/String;Ljava/lang/String;[Lnet/minecraft/client/data/models/model/TextureSlot;)Lnet/minecraft/client/data/models/model/ModelTemplate;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$createTwo(String path) {
        return ModelLocationUtils.decorateBlockModelLocation(path);
    }

    @Redirect(
        method = "createItem(Ljava/lang/String;[Lnet/minecraft/client/data/models/model/TextureSlot;)Lnet/minecraft/client/data/models/model/ModelTemplate;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$createItemOne(String path) {
        return ModelLocationUtils.decorateItemModelLocation(path);
    }

    @Redirect(
        method = "createItem(Ljava/lang/String;Ljava/lang/String;[Lnet/minecraft/client/data/models/model/TextureSlot;)Lnet/minecraft/client/data/models/model/ModelTemplate;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$createItemTwo(String path) {
        return ModelLocationUtils.decorateItemModelLocation(path);
    }
}