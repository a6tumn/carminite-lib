package carminite.interfaces.extensions;

import net.minecraft.client.renderer.SubmitNodeStorage;

import java.util.List;

public interface ISubmitNodeCollectionExtension {
    List<SubmitNodeStorage.MultiLayerBlockModelSubmit> carminite$getMultiLayerBlockModelSubmits();
}