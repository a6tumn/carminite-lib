package carminite.mixin;

import carminite.interfaces.extensions.IEquipmentAssetProviderExtension;
import com.llamalad7.mixinextras.injector.ModifyExpressionValue;
import com.llamalad7.mixinextras.sugar.Local;
import net.minecraft.client.data.models.EquipmentAssetProvider;
import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.Identifier;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Redirect;

import java.util.Map;
import java.util.function.BiConsumer;

@Mixin(EquipmentAssetProvider.class)
public class EquipmentAssetProviderMixin implements IEquipmentAssetProviderExtension {

    @Shadow
    private static void bootstrap(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer) {
        throw new UnsupportedOperationException("Implemented via mixin");
    }

    @Override
    public void carminite$registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        bootstrap(output);
    }

    @Redirect(
        method = "run(Lnet/minecraft/data/CachedOutput;)Ljava/util/concurrent/CompletableFuture;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/client/data/models/EquipmentAssetProvider;bootstrap(Ljava/util/function/BiConsumer;)V"
        )
    )
    private void carminite$redirectBootstrap(
        BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> consumer,
        @Local(name = "equipmentAssets") Map<ResourceKey<EquipmentAsset>, EquipmentClientInfo> equipmentAssets
    ) {
        carminite$registerModels((id, asset) -> {
            if (equipmentAssets.putIfAbsent(id, asset) != null) {
                throw new IllegalStateException("Tried to register equipment asset twice for id: " + id);
            }
        });
    }

    @ModifyExpressionValue(
        method = "onlyHumanoid(Ljava/lang/String;)Lnet/minecraft/client/resources/model/EquipmentClientInfo;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$onlyHumanoid(
        Identifier original,
        @Local(argsOnly = true, name = "name") String name
    ) {
        return Identifier.parse(name);
    }

    @ModifyExpressionValue(
        method = "humanoidAndMountArmor(Ljava/lang/String;)Lnet/minecraft/client/resources/model/EquipmentClientInfo;",
        at = @At(
            value = "INVOKE",
            target = "Lnet/minecraft/resources/Identifier;withDefaultNamespace(Ljava/lang/String;)Lnet/minecraft/resources/Identifier;"
        )
    )
    private static Identifier carminite$humanoidAndMountArmor(
        Identifier original,
        @Local(argsOnly = true, name = "name") String name
    ) {
        return Identifier.parse(name);
    }
}