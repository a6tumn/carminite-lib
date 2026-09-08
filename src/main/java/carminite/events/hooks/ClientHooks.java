package carminite.events.hooks;

import carminite.events.modified.CarminiteComputeFogColorEvent;
import carminite.events.neoforge.*;
import carminite.interfaces.markers.IContinuousUseItem;
import net.minecraft.client.Camera;
import net.minecraft.client.DeltaTracker;
import net.minecraft.client.input.KeyEvent;
import net.minecraft.client.player.ClientInput;
import net.minecraft.client.resources.sounds.SoundInstance;
import net.minecraft.sounds.Music;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import org.joml.Vector4f;
import org.jspecify.annotations.Nullable;

public class ClientHooks {
	private static int slotMainHand = 0;

	public static boolean shouldCauseReequipAnimation(ItemStack from, ItemStack to, int slot) {
		boolean fromInvalid = from.isEmpty();
		boolean toInvalid = to.isEmpty();

		if (fromInvalid && toInvalid) return false;
		if (fromInvalid || toInvalid) return true;

		boolean changed = false;
		if (slot != -1) {
			changed = slot != slotMainHand;
			slotMainHand = slot;
		}

		return ((IContinuousUseItem) from.getItem()).shouldCauseReequipAnimation(from, to, changed);
	}

	public static void onMovementInputUpdate(Player player, ClientInput movementInput) {
		new MovementInputUpdateEvent(player, movementInput).post();
	}

	public static void onKeyInput(KeyEvent keyEvent, int action) {
		new InputEvent.Key(keyEvent, action).post();
	}

	public static float getFieldOfViewModifier(Player entity, float fovModifier, float fovScale) {
		ComputeFovModifierEvent fovModifierEvent = new ComputeFovModifierEvent(entity, fovModifier, fovScale);
		fovModifierEvent.post();
		return fovModifierEvent.getNewFovModifier();
	}

	public static CalculatePlayerTurnEvent getTurnPlayerValues(double mouseSensitivity, boolean cinematicCameraEnabled) {
		var event = new CalculatePlayerTurnEvent(mouseSensitivity, cinematicCameraEnabled);
		event.post();
		return event;
	}

	public static void getFogColor(Camera camera, float partialTick, float fogRed, float fogGreen, float fogBlue, Vector4f dest) {
		dest.set(fogRed, fogGreen, fogBlue, 1F);
		CarminiteComputeFogColorEvent event = new CarminiteComputeFogColorEvent(camera, partialTick, dest);
		event.post();
	}

	@Nullable
	public static Music selectMusic(Music situational, @Nullable SoundInstance playing) {
		SelectMusicEvent e = new SelectMusicEvent(situational, playing);
		e.post();
		return e.getMusic();
	}

	public static void fireRenderFramePre(DeltaTracker partialTick) {
		new RenderFrameEvent.Pre(partialTick).post();
	}

	public static void fireRenderFramePost(DeltaTracker partialTick) {
		new RenderFrameEvent.Post(partialTick).post();
	}
}