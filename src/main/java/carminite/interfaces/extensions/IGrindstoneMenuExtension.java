package carminite.interfaces.extensions;

public interface IGrindstoneMenuExtension {
    default int carminite$getXp() {
        return -2;
    }
}