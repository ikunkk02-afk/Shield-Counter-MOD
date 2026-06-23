package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.minecraft.entity.player.PlayerEntity;

public final class ShieldChargeApi {
	private ShieldChargeApi() {
	}

	public static int getShieldChargeTicks(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getShieldChargeTicks();
	}

	public static int getShieldChargeLevel(PlayerEntity player) {
		return ShieldCounterConfigManager.get().getShieldChargeLevel(getShieldChargeTicks(player));
	}

	public static void resetShieldCharge(PlayerEntity player) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).resetShieldCharge();
	}

	public static int consumeShieldChargeForCounter(
		PlayerEntity player,
		int enchantmentLevel,
		ShieldCounterConfig config
	) {
		return ShieldChargeComponents.SHIELD_CHARGE
			.get(player)
			.consumeShieldChargeForCounter(enchantmentLevel, config);
	}

	public static int getShieldChargeCooldownTicks(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getShieldChargeCooldownTicks();
	}

	public static int getShieldChargeCooldownDurationTicks(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getShieldChargeCooldownDurationTicks();
	}

	public static void setShieldChargeCooldown(PlayerEntity player, int ticks) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).setShieldChargeCooldown(ticks);
	}

	public static void resetShieldChargeCooldown(PlayerEntity player) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).resetShieldChargeCooldown();
	}

	public static boolean isShieldChargeOnCooldown(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).isShieldChargeOnCooldown();
	}

	public static float getStoredShieldDamage(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getStoredShieldDamage();
	}

	public static void addStoredShieldDamage(PlayerEntity player, float amount) {
		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		float maxStoredDamage = (float) config.getEnergyCounterMaxStoredDamage(3);
		ShieldChargeComponents.SHIELD_CHARGE.get(player).addStoredShieldDamage(amount, maxStoredDamage);
	}

	public static void addStoredShieldDamage(PlayerEntity player, float amount, float maxStoredDamage) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).addStoredShieldDamage(amount, maxStoredDamage);
	}

	public static void resetStoredShieldDamage(PlayerEntity player) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).resetStoredShieldDamage();
	}

	public static int getEnergyCounterCooldownTicks(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getEnergyCounterCooldownTicks();
	}

	public static void setEnergyCounterCooldown(PlayerEntity player, int ticks) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).setEnergyCounterCooldown(ticks);
	}

	public static boolean isEnergyCounterOnCooldown(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).isEnergyCounterOnCooldown();
	}

	public static void resetShieldChargeState(PlayerEntity player) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).resetShieldChargeState();
	}
}
