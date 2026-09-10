package carminite.interfaces.extensions;

import net.minecraft.client.renderer.SubmitNodeStorage;

import java.util.List;

public interface ISubmitNodeCollectionExtension {
    default List<SubmitNodeStorage.MultiLayerBlockModelSubmit> carminite$getMultiLayerBlockModelSubmits() {
        throw new AssertionError("Implemented via mixin");
    }
}