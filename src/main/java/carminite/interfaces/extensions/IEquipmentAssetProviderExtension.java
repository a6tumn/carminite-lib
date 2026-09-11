package carminite.interfaces.extensions;

import net.minecraft.client.resources.model.EquipmentClientInfo;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.equipment.EquipmentAsset;

import java.util.function.BiConsumer;

public interface IEquipmentAssetProviderExtension {
    default void carminite$registerModels(BiConsumer<ResourceKey<EquipmentAsset>, EquipmentClientInfo> output) {
        throw new AssertionError("Implemented via mixin");
    }
}