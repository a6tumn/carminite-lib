package carminite.interfaces.extensions;

public interface ITooltipFlagExtension {
    default boolean carminite$hasControlDown() {
        return false;
    }

    default boolean carminite$hasShiftDown() {
        return false;
    }

    default boolean carminite$hasAltDown() {
        return false;
    }

    default boolean carminite$shouldDisplayAllInformation() {
        return false;
    }
}