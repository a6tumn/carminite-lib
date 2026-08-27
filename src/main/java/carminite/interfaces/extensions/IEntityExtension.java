package carminite.interfaces.extensions;

public interface IEntityExtension {
	default boolean canRiderInteract() {
		return false;
	}
}