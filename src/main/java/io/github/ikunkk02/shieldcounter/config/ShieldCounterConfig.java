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
	public static final boolean DEFAULT_ENABLE_SHIELD_CHARGE_COOLDOWN = true;
	public static final int DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL1 = 40;
	public static final int DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL2 = 50;
	public static final int DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL3 = 60;
	public static final int MIN_SHIELD_CHARGE_COOLDOWN_TICKS = 0;
	public static final int MAX_SHIELD_CHARGE_COOLDOWN_TICKS = 200;
	public static final boolean DEFAULT_SHOW_SHIELD_CHARGE_STATUS_MESSAGE = true;
	public static final boolean DEFAULT_ENABLE_SHIELD_CHARGE_HUD = true;
	public static final int DEFAULT_SHIELD_CHARGE_HUD_Y_OFFSET = 14;
	public static final int MIN_SHIELD_CHARGE_HUD_Y_OFFSET = 0;
	public static final int MAX_SHIELD_CHARGE_HUD_Y_OFFSET = 100;
	public static final int DEFAULT_SHIELD_CHARGE_HUD_WIDTH = 60;
	public static final int MIN_SHIELD_CHARGE_HUD_WIDTH = 40;
	public static final int MAX_SHIELD_CHARGE_HUD_WIDTH = 200;
	public static final int DEFAULT_SHIELD_CHARGE_HUD_HEIGHT = 4;
	public static final int MIN_SHIELD_CHARGE_HUD_HEIGHT = 3;
	public static final int MAX_SHIELD_CHARGE_HUD_HEIGHT = 20;
	public static final boolean DEFAULT_ENABLE_SHIELD_COUNTER = true;
	public static final boolean DEFAULT_CONSUME_CHARGE_ON_COUNTER = true;
	public static final double DEFAULT_COUNTER_DURABILITY_COST_MULTIPLIER = 1.0;
	public static final double MIN_COUNTER_DURABILITY_COST_MULTIPLIER = 0.0;
	public static final double MAX_COUNTER_DURABILITY_COST_MULTIPLIER = 5.0;
	public static final double DEFAULT_COUNTER_LEVEL1_BASE_RATIO = 0.25;
	public static final double DEFAULT_COUNTER_LEVEL2_BASE_RATIO = 0.50;
	public static final double DEFAULT_COUNTER_LEVEL3_BASE_RATIO = 1.00;
	public static final double MIN_COUNTER_RATIO = 0.0;
	public static final double MAX_COUNTER_RATIO = 1.0;
	public static final double DEFAULT_COUNTER_KNOCKBACK_LEVEL3_BASE = 1.2;
	public static final double DEFAULT_COUNTER_KNOCKBACK_LEVEL3_FULL_CHARGE = 3.0;
	public static final double MIN_COUNTER_KNOCKBACK = 0.0;
	public static final double MAX_COUNTER_KNOCKBACK = 5.0;
	public static final boolean DEFAULT_ENABLE_ENERGY_COUNTER = true;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL1_THRESHOLD = 20.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL2_THRESHOLD = 16.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL3_THRESHOLD = 12.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL1_MULTIPLIER = 1.5;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL2_MULTIPLIER = 2.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL3_MULTIPLIER = 3.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL1_MAX_STORED_DAMAGE = 30.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL2_MAX_STORED_DAMAGE = 40.0;
	public static final double DEFAULT_ENERGY_COUNTER_LEVEL3_MAX_STORED_DAMAGE = 50.0;
	public static final int DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL1 = 80;
	public static final int DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL2 = 100;
	public static final int DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL3 = 120;
	public static final double MIN_ENERGY_COUNTER_DAMAGE_VALUE = 0.0;
	public static final double MAX_ENERGY_COUNTER_DAMAGE_VALUE = 500.0;
	public static final double MIN_ENERGY_COUNTER_MULTIPLIER = 0.0;
	public static final double MAX_ENERGY_COUNTER_MULTIPLIER = 10.0;
	public static final int MIN_ENERGY_COUNTER_COOLDOWN_TICKS = 0;
	public static final int MAX_ENERGY_COUNTER_COOLDOWN_TICKS = 200;
	public static final boolean DEFAULT_SHOW_ENERGY_COUNTER_MESSAGE = true;

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
	public boolean enableShieldChargeCooldown = DEFAULT_ENABLE_SHIELD_CHARGE_COOLDOWN;
	public int shieldChargeCooldownLevel1 = DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL1;
	public int shieldChargeCooldownLevel2 = DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL2;
	public int shieldChargeCooldownLevel3 = DEFAULT_SHIELD_CHARGE_COOLDOWN_LEVEL3;
	public boolean showShieldChargeStatusMessage = DEFAULT_SHOW_SHIELD_CHARGE_STATUS_MESSAGE;
	public boolean enableShieldChargeHud = DEFAULT_ENABLE_SHIELD_CHARGE_HUD;
	public int shieldChargeHudYOffset = DEFAULT_SHIELD_CHARGE_HUD_Y_OFFSET;
	public int shieldChargeHudWidth = DEFAULT_SHIELD_CHARGE_HUD_WIDTH;
	public int shieldChargeHudHeight = DEFAULT_SHIELD_CHARGE_HUD_HEIGHT;
	public boolean enableShieldCounter = DEFAULT_ENABLE_SHIELD_COUNTER;
	public boolean consumeChargeOnCounter = DEFAULT_CONSUME_CHARGE_ON_COUNTER;
	public double counterDurabilityCostMultiplier = DEFAULT_COUNTER_DURABILITY_COST_MULTIPLIER;
	public double counterLevel1BaseRatio = DEFAULT_COUNTER_LEVEL1_BASE_RATIO;
	public double counterLevel2BaseRatio = DEFAULT_COUNTER_LEVEL2_BASE_RATIO;
	public double counterLevel3BaseRatio = DEFAULT_COUNTER_LEVEL3_BASE_RATIO;
	public double counterKnockbackLevel3Base = DEFAULT_COUNTER_KNOCKBACK_LEVEL3_BASE;
	public double counterKnockbackLevel3FullCharge = DEFAULT_COUNTER_KNOCKBACK_LEVEL3_FULL_CHARGE;
	public boolean enableEnergyCounter = DEFAULT_ENABLE_ENERGY_COUNTER;
	public double energyCounterLevel1Threshold = DEFAULT_ENERGY_COUNTER_LEVEL1_THRESHOLD;
	public double energyCounterLevel2Threshold = DEFAULT_ENERGY_COUNTER_LEVEL2_THRESHOLD;
	public double energyCounterLevel3Threshold = DEFAULT_ENERGY_COUNTER_LEVEL3_THRESHOLD;
	public double energyCounterLevel1Multiplier = DEFAULT_ENERGY_COUNTER_LEVEL1_MULTIPLIER;
	public double energyCounterLevel2Multiplier = DEFAULT_ENERGY_COUNTER_LEVEL2_MULTIPLIER;
	public double energyCounterLevel3Multiplier = DEFAULT_ENERGY_COUNTER_LEVEL3_MULTIPLIER;
	public double energyCounterLevel1MaxStoredDamage = DEFAULT_ENERGY_COUNTER_LEVEL1_MAX_STORED_DAMAGE;
	public double energyCounterLevel2MaxStoredDamage = DEFAULT_ENERGY_COUNTER_LEVEL2_MAX_STORED_DAMAGE;
	public double energyCounterLevel3MaxStoredDamage = DEFAULT_ENERGY_COUNTER_LEVEL3_MAX_STORED_DAMAGE;
	public int energyCounterCooldownLevel1 = DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL1;
	public int energyCounterCooldownLevel2 = DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL2;
	public int energyCounterCooldownLevel3 = DEFAULT_ENERGY_COUNTER_COOLDOWN_LEVEL3;
	public boolean showEnergyCounterMessage = DEFAULT_SHOW_ENERGY_COUNTER_MESSAGE;

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
		validated.enableShieldChargeCooldown = this.enableShieldChargeCooldown;
		validated.shieldChargeCooldownLevel1 = Math.clamp(
			this.shieldChargeCooldownLevel1,
			MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		validated.shieldChargeCooldownLevel2 = Math.clamp(
			this.shieldChargeCooldownLevel2,
			MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		validated.shieldChargeCooldownLevel3 = Math.clamp(
			this.shieldChargeCooldownLevel3,
			MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		validated.showShieldChargeStatusMessage = this.showShieldChargeStatusMessage;
		validated.enableShieldChargeHud = this.enableShieldChargeHud;
		validated.shieldChargeHudYOffset = Math.clamp(
			this.shieldChargeHudYOffset,
			MIN_SHIELD_CHARGE_HUD_Y_OFFSET,
			MAX_SHIELD_CHARGE_HUD_Y_OFFSET
		);
		validated.shieldChargeHudWidth = Math.clamp(
			this.shieldChargeHudWidth,
			MIN_SHIELD_CHARGE_HUD_WIDTH,
			MAX_SHIELD_CHARGE_HUD_WIDTH
		);
		validated.shieldChargeHudHeight = Math.clamp(
			this.shieldChargeHudHeight,
			MIN_SHIELD_CHARGE_HUD_HEIGHT,
			MAX_SHIELD_CHARGE_HUD_HEIGHT
		);
		validated.enableShieldCounter = this.enableShieldCounter;
		validated.consumeChargeOnCounter = this.consumeChargeOnCounter;
		validated.counterDurabilityCostMultiplier = validateFiniteRange(
			this.counterDurabilityCostMultiplier,
			DEFAULT_COUNTER_DURABILITY_COST_MULTIPLIER,
			MIN_COUNTER_DURABILITY_COST_MULTIPLIER,
			MAX_COUNTER_DURABILITY_COST_MULTIPLIER
		);
		validated.counterLevel1BaseRatio = validateFiniteRange(
			this.counterLevel1BaseRatio,
			DEFAULT_COUNTER_LEVEL1_BASE_RATIO,
			MIN_COUNTER_RATIO,
			MAX_COUNTER_RATIO
		);
		validated.counterLevel2BaseRatio = validateFiniteRange(
			this.counterLevel2BaseRatio,
			DEFAULT_COUNTER_LEVEL2_BASE_RATIO,
			MIN_COUNTER_RATIO,
			MAX_COUNTER_RATIO
		);
		validated.counterLevel3BaseRatio = DEFAULT_COUNTER_LEVEL3_BASE_RATIO;
		validated.counterKnockbackLevel3Base = validateFiniteRange(
			this.counterKnockbackLevel3Base,
			DEFAULT_COUNTER_KNOCKBACK_LEVEL3_BASE,
			MIN_COUNTER_KNOCKBACK,
			MAX_COUNTER_KNOCKBACK
		);
		validated.counterKnockbackLevel3FullCharge = validateFiniteRange(
			this.counterKnockbackLevel3FullCharge,
			DEFAULT_COUNTER_KNOCKBACK_LEVEL3_FULL_CHARGE,
			MIN_COUNTER_KNOCKBACK,
			MAX_COUNTER_KNOCKBACK
		);
		validated.enableEnergyCounter = this.enableEnergyCounter;
		validated.energyCounterLevel1Threshold = validateFiniteRange(
			this.energyCounterLevel1Threshold,
			DEFAULT_ENERGY_COUNTER_LEVEL1_THRESHOLD,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterLevel2Threshold = validateFiniteRange(
			this.energyCounterLevel2Threshold,
			DEFAULT_ENERGY_COUNTER_LEVEL2_THRESHOLD,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterLevel3Threshold = validateFiniteRange(
			this.energyCounterLevel3Threshold,
			DEFAULT_ENERGY_COUNTER_LEVEL3_THRESHOLD,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterLevel1Multiplier = validateFiniteRange(
			this.energyCounterLevel1Multiplier,
			DEFAULT_ENERGY_COUNTER_LEVEL1_MULTIPLIER,
			MIN_ENERGY_COUNTER_MULTIPLIER,
			MAX_ENERGY_COUNTER_MULTIPLIER
		);
		validated.energyCounterLevel2Multiplier = validateFiniteRange(
			this.energyCounterLevel2Multiplier,
			DEFAULT_ENERGY_COUNTER_LEVEL2_MULTIPLIER,
			MIN_ENERGY_COUNTER_MULTIPLIER,
			MAX_ENERGY_COUNTER_MULTIPLIER
		);
		validated.energyCounterLevel3Multiplier = validateFiniteRange(
			this.energyCounterLevel3Multiplier,
			DEFAULT_ENERGY_COUNTER_LEVEL3_MULTIPLIER,
			MIN_ENERGY_COUNTER_MULTIPLIER,
			MAX_ENERGY_COUNTER_MULTIPLIER
		);
		validated.energyCounterLevel1MaxStoredDamage = validateFiniteRange(
			this.energyCounterLevel1MaxStoredDamage,
			DEFAULT_ENERGY_COUNTER_LEVEL1_MAX_STORED_DAMAGE,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterLevel2MaxStoredDamage = validateFiniteRange(
			this.energyCounterLevel2MaxStoredDamage,
			DEFAULT_ENERGY_COUNTER_LEVEL2_MAX_STORED_DAMAGE,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterLevel3MaxStoredDamage = validateFiniteRange(
			this.energyCounterLevel3MaxStoredDamage,
			DEFAULT_ENERGY_COUNTER_LEVEL3_MAX_STORED_DAMAGE,
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
		validated.energyCounterCooldownLevel1 = Math.clamp(
			this.energyCounterCooldownLevel1,
			MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
		validated.energyCounterCooldownLevel2 = Math.clamp(
			this.energyCounterCooldownLevel2,
			MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
		validated.energyCounterCooldownLevel3 = Math.clamp(
			this.energyCounterCooldownLevel3,
			MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
		validated.showEnergyCounterMessage = this.showEnergyCounterMessage;
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

	public int getShieldChargeCooldownTicks(int enchantmentLevel) {
		if (!this.enableShieldChargeCooldown || enchantmentLevel <= 0) {
			return 0;
		}

		int configuredTicks;
		if (enchantmentLevel == 1) {
			configuredTicks = this.shieldChargeCooldownLevel1;
		} else if (enchantmentLevel == 2) {
			configuredTicks = this.shieldChargeCooldownLevel2;
		} else {
			configuredTicks = this.shieldChargeCooldownLevel3;
		}
		return Math.clamp(
			configuredTicks,
			MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
	}

	public double getEnergyCounterThreshold(int enchantmentLevel) {
		if (!this.enableEnergyCounter || enchantmentLevel <= 0) {
			return 0.0;
		}
		return Math.clamp(
			this.selectEnergyCounterValue(
				enchantmentLevel,
				this.energyCounterLevel1Threshold,
				this.energyCounterLevel2Threshold,
				this.energyCounterLevel3Threshold
			),
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
	}

	public double getEnergyCounterMultiplier(int enchantmentLevel) {
		if (!this.enableEnergyCounter || enchantmentLevel <= 0) {
			return 0.0;
		}
		return Math.clamp(
			this.selectEnergyCounterValue(
				enchantmentLevel,
				this.energyCounterLevel1Multiplier,
				this.energyCounterLevel2Multiplier,
				this.energyCounterLevel3Multiplier
			),
			MIN_ENERGY_COUNTER_MULTIPLIER,
			MAX_ENERGY_COUNTER_MULTIPLIER
		);
	}

	public double getEnergyCounterMaxStoredDamage(int enchantmentLevel) {
		if (!this.enableEnergyCounter || enchantmentLevel <= 0) {
			return 0.0;
		}
		return Math.clamp(
			this.selectEnergyCounterValue(
				enchantmentLevel,
				this.energyCounterLevel1MaxStoredDamage,
				this.energyCounterLevel2MaxStoredDamage,
				this.energyCounterLevel3MaxStoredDamage
			),
			MIN_ENERGY_COUNTER_DAMAGE_VALUE,
			MAX_ENERGY_COUNTER_DAMAGE_VALUE
		);
	}

	public int getEnergyCounterCooldownTicks(int enchantmentLevel) {
		if (!this.enableEnergyCounter || enchantmentLevel <= 0) {
			return 0;
		}

		int configuredTicks;
		if (enchantmentLevel == 1) {
			configuredTicks = this.energyCounterCooldownLevel1;
		} else if (enchantmentLevel == 2) {
			configuredTicks = this.energyCounterCooldownLevel2;
		} else {
			configuredTicks = this.energyCounterCooldownLevel3;
		}
		return Math.clamp(
			configuredTicks,
			MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
	}

	private double selectEnergyCounterValue(
		int enchantmentLevel,
		double levelOneValue,
		double levelTwoValue,
		double levelThreeValue
	) {
		if (enchantmentLevel == 1) {
			return levelOneValue;
		}
		if (enchantmentLevel == 2) {
			return levelTwoValue;
		}
		return levelThreeValue;
	}

	private static double validateFiniteRange(double value, double defaultValue, double minimum, double maximum) {
		if (!Double.isFinite(value)) {
			return defaultValue;
		}

		return Math.clamp(value, minimum, maximum);
	}
}
