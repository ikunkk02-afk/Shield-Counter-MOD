package io.github.ikunkk02.shieldcounter.config;

public class ShieldCounterConfig {
	public static final boolean DEFAULT_ENABLE_FALL_SHIELD_BLOCK = true;
	public static final double DEFAULT_FALL_DAMAGE_REDUCTION = 0.5;
	public static final double MIN_FALL_DAMAGE_REDUCTION = 0.0;
	public static final double MAX_FALL_DAMAGE_REDUCTION = 0.95;
	public static final double DEFAULT_DURABILITY_COST_MULTIPLIER = 1.0;
	public static final double MIN_DURABILITY_COST_MULTIPLIER = 0.0;
	public static final double MAX_DURABILITY_COST_MULTIPLIER = 5.0;
	public static final boolean DEFAULT_REQUIRE_SHIELD_RAISE_TIME = true;
	public static final int DEFAULT_MIN_SHIELD_RAISE_TICKS = 10;
	public static final int MIN_SHIELD_RAISE_TICKS = 0;
	public static final int MAX_SHIELD_RAISE_TICKS = 60;
	public static final boolean DEFAULT_ENABLE_SHIELD_CHARGE = true;
	public static final int DEFAULT_MAX_SHIELD_CHARGE_TICKS = 60;
	public static final int MIN_MAX_SHIELD_CHARGE_TICKS = 20;
	public static final int MAX_MAX_SHIELD_CHARGE_TICKS = 200;
	public static final int DEFAULT_CHARGE_STAGE_ONE_TICKS = 20;
	public static final int DEFAULT_CHARGE_STAGE_TWO_TICKS = 40;
	public static final int DEFAULT_CHARGE_STAGE_THREE_TICKS = 60;
	public static final int MIN_CHARGE_STAGE_TICKS = 1;

	public boolean enableFallShieldBlock = DEFAULT_ENABLE_FALL_SHIELD_BLOCK;
	public double fallDamageReduction = DEFAULT_FALL_DAMAGE_REDUCTION;
	public double durabilityCostMultiplier = DEFAULT_DURABILITY_COST_MULTIPLIER;
	public boolean requireShieldRaiseTime = DEFAULT_REQUIRE_SHIELD_RAISE_TIME;
	public int minShieldRaiseTicks = DEFAULT_MIN_SHIELD_RAISE_TICKS;
	public boolean enableShieldCharge = DEFAULT_ENABLE_SHIELD_CHARGE;
	public int maxShieldChargeTicks = DEFAULT_MAX_SHIELD_CHARGE_TICKS;
	public int chargeStageOneTicks = DEFAULT_CHARGE_STAGE_ONE_TICKS;
	public int chargeStageTwoTicks = DEFAULT_CHARGE_STAGE_TWO_TICKS;
	public int chargeStageThreeTicks = DEFAULT_CHARGE_STAGE_THREE_TICKS;

	public ShieldCounterConfig validatedCopy() {
		ShieldCounterConfig validated = new ShieldCounterConfig();
		validated.enableFallShieldBlock = this.enableFallShieldBlock;
		validated.fallDamageReduction = validateFiniteRange(
			this.fallDamageReduction,
			DEFAULT_FALL_DAMAGE_REDUCTION,
			MIN_FALL_DAMAGE_REDUCTION,
			MAX_FALL_DAMAGE_REDUCTION
		);
		validated.durabilityCostMultiplier = validateFiniteRange(
			this.durabilityCostMultiplier,
			DEFAULT_DURABILITY_COST_MULTIPLIER,
			MIN_DURABILITY_COST_MULTIPLIER,
			MAX_DURABILITY_COST_MULTIPLIER
		);
		validated.requireShieldRaiseTime = this.requireShieldRaiseTime;
		validated.minShieldRaiseTicks = Math.clamp(
			this.minShieldRaiseTicks,
			MIN_SHIELD_RAISE_TICKS,
			MAX_SHIELD_RAISE_TICKS
		);
		validated.enableShieldCharge = this.enableShieldCharge;
		validated.maxShieldChargeTicks = Math.clamp(
			this.maxShieldChargeTicks,
			MIN_MAX_SHIELD_CHARGE_TICKS,
			MAX_MAX_SHIELD_CHARGE_TICKS
		);
		validated.chargeStageOneTicks = Math.clamp(
			this.chargeStageOneTicks,
			MIN_CHARGE_STAGE_TICKS,
			validated.maxShieldChargeTicks - 2
		);
		validated.chargeStageTwoTicks = Math.clamp(
			this.chargeStageTwoTicks,
			validated.chargeStageOneTicks + 1,
			validated.maxShieldChargeTicks - 1
		);
		validated.chargeStageThreeTicks = Math.clamp(
			this.chargeStageThreeTicks,
			validated.chargeStageTwoTicks + 1,
			validated.maxShieldChargeTicks
		);
		return validated;
	}

	public float calculateReducedDamage(float incomingDamage) {
		if (incomingDamage <= 0.0F) {
			return incomingDamage;
		}

		double reduction = validateFiniteRange(
			this.fallDamageReduction,
			DEFAULT_FALL_DAMAGE_REDUCTION,
			MIN_FALL_DAMAGE_REDUCTION,
			MAX_FALL_DAMAGE_REDUCTION
		);
		return (float) (incomingDamage * (1.0 - reduction));
	}

	public int calculateDurabilityCost(float preventedDamage) {
		double multiplier = validateFiniteRange(
			this.durabilityCostMultiplier,
			DEFAULT_DURABILITY_COST_MULTIPLIER,
			MIN_DURABILITY_COST_MULTIPLIER,
			MAX_DURABILITY_COST_MULTIPLIER
		);
		if (preventedDamage <= 0.0F || multiplier <= 0.0) {
			return 0;
		}

		return Math.max(1, (int) Math.ceil(preventedDamage * multiplier));
	}

	public boolean isRaiseTimeSatisfied(int itemUseTicks) {
		if (!this.requireShieldRaiseTime) {
			return true;
		}

		int requiredTicks = Math.clamp(
			this.minShieldRaiseTicks,
			MIN_SHIELD_RAISE_TICKS,
			MAX_SHIELD_RAISE_TICKS
		);
		return itemUseTicks >= requiredTicks;
	}

	public int getShieldChargeLevel(int shieldChargeTicks) {
		int ticks = Math.max(0, shieldChargeTicks);
		if (ticks >= this.chargeStageThreeTicks) {
			return 3;
		}
		if (ticks >= this.chargeStageTwoTicks) {
			return 2;
		}
		if (ticks >= this.chargeStageOneTicks) {
			return 1;
		}
		return 0;
	}

	private static double validateFiniteRange(double value, double defaultValue, double minimum, double maximum) {
		if (!Double.isFinite(value)) {
			return defaultValue;
		}

		return Math.clamp(value, minimum, maximum);
	}
}
