package carminite.mixin;

import carminite.interfaces.extensions.ILevelExtractorExtension;
import com.llamalad7.mixinextras.injector.wrapoperation.Operation;
import com.llamalad7.mixinextras.injector.wrapoperation.WrapOperation;
import com.llamalad7.mixinextras.sugar.Local;
import com.mojang.blaze3d.vertex.PoseStack;
import net.minecraft.client.Camera;
import net.minecraft.client.multiplayer.ClientLevel;
import net.minecraft.client.renderer.LevelRenderer;
import net.minecraft.client.renderer.blockentity.state.BlockEntityRenderState;
import net.minecraft.client.renderer.chunk.SectionRenderDispatcher;
import net.minecraft.client.renderer.culling.Frustum;
import net.minecraft.client.renderer.extract.LevelExtractor;
import net.minecraft.client.renderer.feature.ModelFeatureRenderer;
import net.minecraft.client.renderer.state.level.LevelRenderState;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.BlockDestructionProgress;
import net.minecraft.util.Util;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;

import java.util.Iterator;
import java.util.List;
import java.util.SortedSet;

@Mixin(LevelExtractor.class)
public class LevelExtractorMixin implements ILevelExtractorExtension {

	@Shadow
	@Final
	private LevelRenderer levelRenderer;

	@Shadow
	private @Nullable ClientLevel level;

	@WrapOperation(
		method = "extract(Lnet/minecraft/client/DeltaTracker;Lnet/minecraft/client/Camera;F)V",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/client/renderer/extract/LevelExtractor;extractVisibleBlockEntities(Lnet/minecraft/client/Camera;FLnet/minecraft/client/renderer/state/level/LevelRenderState;)V"
		)
	)
	private void carminite$extract(
		LevelExtractor instance,
		Camera camera,
		float deltaPartialTick,
		LevelRenderState levelRenderState,
		Operation<Void> original,
		@Local(name = "cullFrustum") Frustum cullFrustum
	) {
		carminite$extractVisibleBlockEntities(camera, deltaPartialTick, levelRenderState, cullFrustum);
	}

	@Override
	public void carminite$extractVisibleBlockEntities(Camera camera, float deltaPartialTick, LevelRenderState levelRenderState, @Nullable Frustum cullFrustum) {
		Vec3 cameraPos = camera.position();
		double camX = cameraPos.x();
		double camY = cameraPos.y();
		double camZ = cameraPos.z();
		PoseStack poseStack = new PoseStack();

		for (SectionRenderDispatcher.RenderSection section : this.levelRenderer.visibleSections()) {
			List<BlockEntity> renderableBlockEntities = section.getSectionMesh().getRenderableBlockEntities();
			if (!renderableBlockEntities.isEmpty() && !(section.getVisibility(Util.getMillis()) < 0.3F)) {
				for (BlockEntity blockEntity : renderableBlockEntities) {
					BlockPos blockPos = blockEntity.getBlockPos();
					SortedSet<BlockDestructionProgress> progresses = this.level.destructionProgress().get(blockPos.asLong());
					ModelFeatureRenderer.CrumblingOverlay breakProgress;
					if (progresses != null && !progresses.isEmpty()) {
						poseStack.pushPose();
						poseStack.translate(blockPos.getX() - camX, blockPos.getY() - camY, blockPos.getZ() - camZ);
						breakProgress = new ModelFeatureRenderer.CrumblingOverlay(progresses.last().getProgress(), poseStack.last());
						poseStack.popPose();
					} else {
						breakProgress = null;
					}

					BlockEntityRenderState state = this.levelRenderer
						.blockEntityRenderDispatcher()
						.carminite$tryExtractRenderState(blockEntity, deltaPartialTick, breakProgress, false, cullFrustum);
					if (state != null) {
						levelRenderState.blockEntityRenderStates.add(state);
					}
				}
			}
		}

		Iterator<BlockEntity> iterator = this.level.getGloballyRenderedBlockEntities().iterator();

		while (iterator.hasNext()) {
			BlockEntity blockEntity = iterator.next();
			if (blockEntity.isRemoved()) {
				iterator.remove();
			} else {
				BlockEntityRenderState state = this.levelRenderer
					.blockEntityRenderDispatcher()
					.carminite$tryExtractRenderState(blockEntity, deltaPartialTick, null, true, cullFrustum);
				if (state != null) {
					levelRenderState.blockEntityRenderStates.add(state);
				}
			}
		}
	}
}
