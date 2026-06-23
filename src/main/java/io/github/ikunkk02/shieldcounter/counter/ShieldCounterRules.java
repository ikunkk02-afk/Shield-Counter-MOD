package io.github.ikunkk02.shieldcounter.counter;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;

public final class ShieldCounterRules {
	private static final double[] LEVEL_ONE_CHARGE_BONUSES = {0.0, 0.10, 0.20, 0.35};
	private static final double[] LEVEL_TWO_CHARGE_BONUSES = {0.0, 0.15, 0.30, 0.40};
	private static final double[] LEVEL_THREE_KNOCKBACK_WEIGHTS = {0.0, 0.30, 0.60, 1.0};

	private ShieldCounterRules() {
	}

	public static boolean shouldCounter(
		boolean enabled,
		boolean serverSide,
		boolean usingShield,
		int enchantmentLevel,
		float originalDamage,
		boolean hasLivingAttacker,
		boolean reflectedDamage
	) {
		return enabled
			&& serverSide
			&& usingShield
			&& enchantmentLevel > 0
			&& originalDamage > 0.0F
			&& hasLivingAttacker
			&& !reflectedDamage;
	}

	public static double calculateReflectRatio(
		int enchantmentLevel,
		int chargeLevel,
		ShieldCounterConfig config
	) {
		if (enchantmentLevel <= 0) {
			return 0.0;
		}
		if (enchantmentLevel >= 3) {
			return 1.0;
		}

		int stage = Math.clamp(chargeLevel, 0, 3);
		double baseRatio;
		double chargeBonus;
		if (enchantmentLevel == 1) {
			baseRatio = Math.clamp(config.counterLevel1BaseRatio, 0.0, 1.0);
			chargeBonus = LEVEL_ONE_CHARGE_BONUSES[stage];
		} else {
			baseRatio = Math.clamp(config.counterLevel2BaseRatio, 0.0, 1.0);
			chargeBonus = LEVEL_TWO_CHARGE_BONUSES[stage];
		}
		return Math.clamp(baseRatio + chargeBonus, 0.0, 1.0);
	}

	public static int effectiveChargeLevel(int chargeLevel, boolean chargeOnCooldown) {
		if (chargeOnCooldown) {
			return 0;
		}
		return Math.clamp(chargeLevel, 0, 3);
	}

	public static int calculateChargeCooldownTicks(
		int enchantmentLevel,
		int chargeLevel,
		ShieldCounterConfig config
	) {
		if (effectiveChargeLevel(chargeLevel, false) < 3) {
			return 0;
		}
		return config.getShieldChargeCooldownTicks(enchantmentLevel);
	}

	public static double calculateKnockback(
		int enchantmentLevel,
		int chargeLevel,
		ShieldCounterConfig config
	) {
		if (enchantmentLevel < 3) {
			return 0.0;
		}

		int stage = Math.clamp(chargeLevel, 0, 3);
		double base = Math.max(0.0, config.counterKnockbackLevel3Base);
		double fullCharge = Math.max(0.0, config.counterKnockbackLevel3FullCharge);
		return base + (fullCharge - base) * LEVEL_THREE_KNOCKBACK_WEIGHTS[stage];
	}

	public static int scaleDurabilityCost(int vanillaCost, double multiplier) {
		if (vanillaCost <= 0 || multiplier <= 0.0) {
			return 0;
		}

		double boundedMultiplier = Double.isFinite(multiplier)
			? Math.clamp(multiplier, 0.0, 5.0)
			: ShieldCounterConfig.DEFAULT_COUNTER_DURABILITY_COST_MULTIPLIER;
		return Math.max(1, (int) Math.ceil(vanillaCost * boundedMultiplier));
	}
}
