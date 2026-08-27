package carminite.interfaces.extensions;

public interface IEntityExtension {
	default boolean carminite$canRiderInteract() {
		return false;
	}
}