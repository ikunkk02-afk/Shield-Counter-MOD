package io.github.ikunkk02.shieldcounter.counter;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;

public final class EnergyCounterRules {
	private static final double LEVEL_THREE_RELEASE_KNOCKBACK = 1.6D;

	private EnergyCounterRules() {
	}

	public static boolean shouldProcess(
		boolean enabled,
		boolean serverSide,
		boolean usingShield,
		int enchantmentLevel,
		float blockedDamage,
		boolean hasLivingAttacker,
		boolean reflectedDamage,
		boolean onCooldown
	) {
		return enabled
			&& serverSide
			&& usingShield
			&& enchantmentLevel > 0
			&& blockedDamage > 0.0F
			&& hasLivingAttacker
			&& !reflectedDamage
			&& !onCooldown;
	}

	public static boolean shouldRelease(float storedDamage, int enchantmentLevel, ShieldCounterConfig config) {
		float threshold = getThreshold(enchantmentLevel, config);
		return threshold > 0.0F && storedDamage >= threshold;
	}

	public static float calculateStoredDamageAfterBlock(
		float currentStoredDamage,
		float blockedDamage,
		int enchantmentLevel,
		ShieldCounterConfig config
	) {
		float maxStoredDamage = getMaxStoredDamage(enchantmentLevel, config);
		if (maxStoredDamage <= 0.0F || blockedDamage <= 0.0F) {
			return Math.clamp(currentStoredDamage, 0.0F, maxStoredDamage);
		}
		return Math.clamp(currentStoredDamage + blockedDamage, 0.0F, maxStoredDamage);
	}

	public static float calculateReleaseDamage(
		float storedDamage,
		int enchantmentLevel,
		ShieldCounterConfig config
	) {
		return (float) (Math.max(0.0F, storedDamage) * getMultiplier(enchantmentLevel, config));
	}

	public static float getThreshold(int enchantmentLevel, ShieldCounterConfig config) {
		return (float) config.getEnergyCounterThreshold(enchantmentLevel);
	}

	public static float getMaxStoredDamage(int enchantmentLevel, ShieldCounterConfig config) {
		return (float) config.getEnergyCounterMaxStoredDamage(enchantmentLevel);
	}

	public static double getMultiplier(int enchantmentLevel, ShieldCounterConfig config) {
		return config.getEnergyCounterMultiplier(enchantmentLevel);
	}

	public static int getCooldownTicks(int enchantmentLevel, ShieldCounterConfig config) {
		return config.getEnergyCounterCooldownTicks(enchantmentLevel);
	}

	public static double calculateReleaseKnockback(int enchantmentLevel) {
		return enchantmentLevel >= 3 ? LEVEL_THREE_RELEASE_KNOCKBACK : 0.0D;
	}

	public static int calculateReleaseDurabilityCost(float releaseDamage) {
		return Math.max(1, (int) Math.ceil(Math.max(0.0F, releaseDamage) / 4.0F));
	}
}
