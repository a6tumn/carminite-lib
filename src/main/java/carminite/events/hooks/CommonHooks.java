package carminite.events.hooks;

import carminite.events.neoforge.*;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.protocol.game.ClientboundBlockUpdatePacket;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.damagesource.DamageSource;
import net.minecraft.world.entity.Entity;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.equipment.Equippable;
import net.minecraft.world.level.GameType;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.GameMasterBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;

import java.util.EnumMap;

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

	public static boolean onPlayerAttackTarget(Player player, Entity target) {
		if (new AttackEntityEvent(player, target).post().isCanceled())
			return false;
		ItemStack stack = player.getMainHandItem();
		return stack.isEmpty() || !stack.getItem().carminite$onLeftClickEntity(stack, player, target);
	}
}