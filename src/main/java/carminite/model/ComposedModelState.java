package carminite.model;

import com.mojang.math.Transformation;
import net.minecraft.client.renderer.block.dispatch.ModelState;
import net.minecraft.core.Direction;
import org.joml.Matrix4fc;

public record ComposedModelState(ModelState parent, Transformation transformation) implements ModelState {
    public ComposedModelState(ModelState parent, Transformation transformation) {
        this.parent = parent;
        this.transformation = parent.transformation().compose(transformation);
    }

    public Matrix4fc faceTransformation(Direction side) {
        return this.parent.faceTransformation(side);
    }

    public Matrix4fc inverseFaceTransformation(Direction side) {
        return this.parent.inverseFaceTransformation(side);
    }
}