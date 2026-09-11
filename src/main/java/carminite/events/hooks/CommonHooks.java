package carminite.events.hooks;

import carminite.events.neoforge.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.Container;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.ExperienceOrb;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.inventory.AnvilMenu;
import net.minecraft.world.inventory.ContainerLevelAccess;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.component.ItemAttributeModifiers;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraft.world.phys.Vec3;
import org.jspecify.annotations.Nullable;

import java.util.EnumMap;
import java.util.function.Function;

public class CommonHooks {
	public static BreakBlockEvent fireBlockBreak(Level level, GameType gameType, Player player, BlockPos pos, BlockState state) {
		boolean preCancelEvent = false;

		ItemStack itemstack = player.getMainHandItem();
		if (!itemstack.isEmpty() && !itemstack.canDestroyBlock(state, level, pos, player)) {
			preCancelEvent = true;
		}

		if (player.blockActionRestricted(level, pos, gameType)) {
			preCancelEvent = true;
		}

		if (state.getBlock() instanceof GameMasterBlock && !player.canUseGameMasterBlocks()) {
			preCancelEvent = true;
		}

		var event = new BreakBlockEvent(level, pos, state, player);
		event.setCanceled(preCancelEvent);
		event = event.post();

		if (event.isCanceled() && event.shouldNotifyClient() && player instanceof ServerPlayer sp) {
			sp.connection.send(new ClientboundBlockUpdatePacket(pos, state));
		}

		return event;
	}

	public static void onAnvilUpdate(AnvilMenu menu, ItemStack leftInput, ItemStack rightInput, Container resultSlot, @Nullable String name, Player player) {
		if (!leftInput.isEmpty()) {
			var event = new AnvilUpdateEvent(leftInput, rightInput, name, resultSlot.getItem(0), menu.getCost(), menu.repairItemCountCost, player);
			if (event.post().isCanceled()) {
				resultSlot.setItem(0, ItemStack.EMPTY);
				menu.cost.set(0);
				menu.repairItemCountCost = 0;
				return;
			}

			resultSlot.setItem(0, event.getOutput());
			menu.cost.set(Math.max(0, event.getXpCost()));
			menu.repairItemCountCost = event.getMaterialCost();
		}
	}

	public static int onGrindstoneChange(ItemStack top, ItemStack bottom, Container outputSlot, int xp) {
		GrindstoneEvent.OnPlaceItem e = new GrindstoneEvent.OnPlaceItem(top, bottom, xp);
		if (e.post().isCanceled()) {
			outputSlot.setItem(0, ItemStack.EMPTY);
			return -1;
		}
		if (e.getOutput().isEmpty())
			return Integer.MIN_VALUE;

		outputSlot.setItem(0, e.getOutput());
		return e.getXp();
	}

	public static boolean onGrindstoneTake(Container inputSlots, ContainerLevelAccess access, Player player, Function<Level, Integer> xpFunction) {
		access.execute((l, p) -> {
			int xp = xpFunction.apply(l);
			GrindstoneEvent.OnTakeItem e = new GrindstoneEvent.OnTakeItem(access, player, inputSlots.getItem(0), inputSlots.getItem(1), xp);
			if (e.post().isCanceled()) {
				return;
			}
			if (l instanceof ServerLevel) {
				ExperienceOrb.award((ServerLevel) l, Vec3.atCenterOf(p), e.getXp());
			}
			l.levelEvent(1042, p, 0);
			inputSlots.setItem(0, e.getNewTopItem());
			inputSlots.setItem(1, e.getNewBottomItem());
			inputSlots.setChanged();
		});
		return true;
	}

	public static void onArmorHurt(DamageSource source, EquipmentSlot[] slots, float damage, LivingEntity armoredEntity) {
		EnumMap<EquipmentSlot, ArmorHurtEvent.ArmorEntry> armorMap = new EnumMap<>(EquipmentSlot.class);
		for (EquipmentSlot slot : slots) {
			ItemStack armorPiece = armoredEntity.getItemBySlot(slot);
			if (armorPiece.isEmpty()) continue;
			Equippable equippable = armorPiece.get(DataComponents.EQUIPPABLE);
			float damageAfterFireResist = (equippable != null && equippable.damageOnHurt() && armorPiece.isDamageableItem() && armorPiece.canBeHurtBy(source)) ? damage : 0;
			armorMap.put(slot, new ArmorHurtEvent.ArmorEntry(armorPiece, damageAfterFireResist));
		}

		ArmorHurtEvent event = new ArmorHurtEvent(armorMap, armoredEntity, source).post();
		if (event.isCanceled()) return;
		event.getArmorMap().forEach((slot, entry) -> entry.armorItemStack.hurtAndBreak((int) entry.newDamage, armoredEntity, slot));
	}

	public static boolean onLivingDeath(LivingEntity entity, DamageSource src) {
		return new LivingDeathEvent(entity, src).post().isCanceled();
	}

	public static PlayerInteractEvent.RightClickBlock onRightClickBlock(Player player, InteractionHand hand, BlockPos pos, BlockHitResult hitVec) {
		PlayerInteractEvent.RightClickBlock evt = new PlayerInteractEvent.RightClickBlock(player, hand, pos, hitVec);
		evt.post();
		return evt;
	}

	public static void onEmptyLeftClick(Player player) {
		new PlayerInteractEvent.LeftClickEmpty(player).post();
	}

	public static void onLivingJump(LivingEntity entity) {
		new LivingEvent.LivingJumpEvent(entity).post();
	}

	public static LivingFallEvent onLivingFall(LivingEntity entity, double distance, float damageMultiplier) {
		LivingFallEvent event = new LivingFallEvent(entity, distance, damageMultiplier);
		return event.post();
	}

	public static boolean onPlayerAttackTarget(Player player, Entity target) {
		if (new AttackEntityEvent(player, target).post().isCanceled())
			return false;
		ItemStack stack = player.getMainHandItem();
		return stack.isEmpty() || !stack.getItem().carminite$onLeftClickEntity(stack, player, target);
	}

	public static ItemAttributeModifiers computeModifiedAttributes(ItemStack stack, ItemAttributeModifiers defaultModifiers) {
		ItemAttributeModifierEvent event = new ItemAttributeModifierEvent(stack, defaultModifiers);
		event.post();
		return event.build();
	}
}