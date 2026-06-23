package io.github.ikunkk02.shieldcounter.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import org.junit.jupiter.api.Test;

class ShieldCounterConfigTest {
	@Test
	void defaultsMatchDocumentedGameplayValues() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertTrue(config.enableFallShieldBlock);
		assertEquals(0.5, config.fallDamageReduction);
		assertEquals(1.0, config.durabilityCostMultiplier);
		assertTrue(config.requireShieldRaiseTime);
		assertEquals(10, config.minShieldRaiseTicks);
		assertTrue(config.enableShieldCharge);
		assertEquals(60, config.maxShieldChargeTicks);
		assertEquals(20, config.chargeStageOneTicks);
		assertEquals(40, config.chargeStageTwoTicks);
		assertEquals(60, config.chargeStageThreeTicks);
		assertTrue(config.enableShieldChargeCooldown);
		assertEquals(40, config.shieldChargeCooldownLevel1);
		assertEquals(50, config.shieldChargeCooldownLevel2);
		assertEquals(60, config.shieldChargeCooldownLevel3);
		assertTrue(config.showShieldChargeStatusMessage);
		assertTrue(config.enableShieldCounter);
		assertTrue(config.consumeChargeOnCounter);
		assertEquals(1.0, config.counterDurabilityCostMultiplier);
		assertEquals(0.25, config.counterLevel1BaseRatio);
		assertEquals(0.50, config.counterLevel2BaseRatio);
		assertEquals(1.00, config.counterLevel3BaseRatio);
		assertEquals(1.2, config.counterKnockbackLevel3Base);
		assertEquals(3.0, config.counterKnockbackLevel3FullCharge);
		assertTrue(config.enableEnergyCounter);
		assertEquals(20.0, config.energyCounterLevel1Threshold);
		assertEquals(16.0, config.energyCounterLevel2Threshold);
		assertEquals(12.0, config.energyCounterLevel3Threshold);
		assertEquals(1.5, config.energyCounterLevel1Multiplier);
		assertEquals(2.0, config.energyCounterLevel2Multiplier);
		assertEquals(3.0, config.energyCounterLevel3Multiplier);
		assertEquals(30.0, config.energyCounterLevel1MaxStoredDamage);
		assertEquals(40.0, config.energyCounterLevel2MaxStoredDamage);
		assertEquals(50.0, config.energyCounterLevel3MaxStoredDamage);
		assertEquals(80, config.energyCounterCooldownLevel1);
		assertEquals(100, config.energyCounterCooldownLevel2);
		assertEquals(120, config.energyCounterCooldownLevel3);
		assertTrue(config.showEnergyCounterMessage);
	}

	@Test
	void validationKeepsChargeStagesStrictlyIncreasingAndReachable() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.maxShieldChargeTicks = 50;
		config.chargeStageOneTicks = 45;
		config.chargeStageTwoTicks = 10;
		config.chargeStageThreeTicks = 20;

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(50, validated.maxShieldChargeTicks);
		assertEquals(45, validated.chargeStageOneTicks);
		assertEquals(46, validated.chargeStageTwoTicks);
		assertEquals(47, validated.chargeStageThreeTicks);

		config.maxShieldChargeTicks = 500;
		config.chargeStageOneTicks = 500;
		config.chargeStageTwoTicks = 500;
		config.chargeStageThreeTicks = 500;
		validated = config.validatedCopy();

		assertEquals(200, validated.maxShieldChargeTicks);
		assertEquals(198, validated.chargeStageOneTicks);
		assertEquals(199, validated.chargeStageTwoTicks);
		assertEquals(200, validated.chargeStageThreeTicks);

		config.maxShieldChargeTicks = -100;
		config.chargeStageOneTicks = -100;
		config.chargeStageTwoTicks = -100;
		config.chargeStageThreeTicks = -100;
		validated = config.validatedCopy();

		assertEquals(20, validated.maxShieldChargeTicks);
		assertEquals(1, validated.chargeStageOneTicks);
		assertEquals(2, validated.chargeStageTwoTicks);
		assertEquals(3, validated.chargeStageThreeTicks);
	}

	@Test
	void calculatesDefaultAndCustomShieldChargeLevels() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(0, config.getShieldChargeLevel(0));
		assertEquals(0, config.getShieldChargeLevel(19));
		assertEquals(1, config.getShieldChargeLevel(20));
		assertEquals(1, config.getShieldChargeLevel(39));
		assertEquals(2, config.getShieldChargeLevel(40));
		assertEquals(2, config.getShieldChargeLevel(59));
		assertEquals(3, config.getShieldChargeLevel(60));
		assertEquals(3, config.getShieldChargeLevel(200));

		config.chargeStageOneTicks = 5;
		config.chargeStageTwoTicks = 10;
		config.chargeStageThreeTicks = 15;

		assertEquals(0, config.getShieldChargeLevel(4));
		assertEquals(1, config.getShieldChargeLevel(5));
		assertEquals(2, config.getShieldChargeLevel(10));
		assertEquals(3, config.getShieldChargeLevel(15));
	}

	@Test
	void validationClampsValuesToSupportedRanges() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.fallDamageReduction = 2.0;
		config.durabilityCostMultiplier = -1.0;
		config.minShieldRaiseTicks = 100;
		config.shieldChargeCooldownLevel1 = -1;
		config.shieldChargeCooldownLevel2 = 250;
		config.shieldChargeCooldownLevel3 = 201;
		config.energyCounterLevel1Threshold = -10.0;
		config.energyCounterLevel2Multiplier = Double.POSITIVE_INFINITY;
		config.energyCounterLevel3MaxStoredDamage = -5.0;
		config.energyCounterCooldownLevel3 = 500;

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(0.95, validated.fallDamageReduction);
		assertEquals(0.0, validated.durabilityCostMultiplier);
		assertEquals(60, validated.minShieldRaiseTicks);
		assertEquals(0, validated.shieldChargeCooldownLevel1);
		assertEquals(200, validated.shieldChargeCooldownLevel2);
		assertEquals(200, validated.shieldChargeCooldownLevel3);
		assertEquals(0.0, validated.energyCounterLevel1Threshold);
		assertEquals(2.0, validated.energyCounterLevel2Multiplier);
		assertEquals(0.0, validated.energyCounterLevel3MaxStoredDamage);
		assertEquals(200, validated.energyCounterCooldownLevel3);

		config.fallDamageReduction = -1.0;
		config.durabilityCostMultiplier = 10.0;
		config.minShieldRaiseTicks = -5;
		config.shieldChargeCooldownLevel1 = 12;
		config.shieldChargeCooldownLevel2 = 34;
		config.shieldChargeCooldownLevel3 = 56;
		config.energyCounterLevel1Threshold = 11.0;
		config.energyCounterLevel2Multiplier = 2.5;
		config.energyCounterLevel3MaxStoredDamage = 45.0;
		config.energyCounterCooldownLevel3 = 90;
		validated = config.validatedCopy();

		assertEquals(0.0, validated.fallDamageReduction);
		assertEquals(5.0, validated.durabilityCostMultiplier);
		assertEquals(0, validated.minShieldRaiseTicks);
		assertEquals(12, validated.shieldChargeCooldownLevel1);
		assertEquals(34, validated.shieldChargeCooldownLevel2);
		assertEquals(56, validated.shieldChargeCooldownLevel3);
		assertEquals(11.0, validated.energyCounterLevel1Threshold);
		assertEquals(2.5, validated.energyCounterLevel2Multiplier);
		assertEquals(45.0, validated.energyCounterLevel3MaxStoredDamage);
		assertEquals(90, validated.energyCounterCooldownLevel3);
	}

	@Test
	void validationRestoresDefaultsForNonFiniteNumbers() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.fallDamageReduction = Double.NaN;
		config.durabilityCostMultiplier = Double.POSITIVE_INFINITY;
		config.counterDurabilityCostMultiplier = Double.NaN;
		config.counterLevel1BaseRatio = Double.NEGATIVE_INFINITY;
		config.counterKnockbackLevel3FullCharge = Double.POSITIVE_INFINITY;
		config.energyCounterLevel2Threshold = Double.NaN;
		config.energyCounterLevel3Multiplier = Double.NEGATIVE_INFINITY;

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(0.5, validated.fallDamageReduction);
		assertEquals(1.0, validated.durabilityCostMultiplier);
		assertEquals(1.0, validated.counterDurabilityCostMultiplier);
		assertEquals(0.25, validated.counterLevel1BaseRatio);
		assertEquals(3.0, validated.counterKnockbackLevel3FullCharge);
		assertEquals(16.0, validated.energyCounterLevel2Threshold);
		assertEquals(3.0, validated.energyCounterLevel3Multiplier);
	}

	@Test
	void validationClampsShieldCounterValues() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.counterDurabilityCostMultiplier = 9.0;
		config.counterLevel1BaseRatio = -1.0;
		config.counterLevel2BaseRatio = 2.0;
		config.counterLevel3BaseRatio = 5.0;
		config.counterKnockbackLevel3Base = -1.0;
		config.counterKnockbackLevel3FullCharge = 9.0;

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(5.0, validated.counterDurabilityCostMultiplier);
		assertEquals(0.0, validated.counterLevel1BaseRatio);
		assertEquals(1.0, validated.counterLevel2BaseRatio);
		assertEquals(1.0, validated.counterLevel3BaseRatio);
		assertEquals(0.0, validated.counterKnockbackLevel3Base);
		assertEquals(5.0, validated.counterKnockbackLevel3FullCharge);

		config.counterLevel3BaseRatio = 0.25;
		validated = config.validatedCopy();

		assertEquals(1.0, validated.counterLevel3BaseRatio);
	}

	@Test
	void calculatesDamageRemainingAfterReduction() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		config.fallDamageReduction = 0.0;
		assertEquals(10.0F, config.calculateReducedDamage(10.0F));

		config.fallDamageReduction = 0.5;
		assertEquals(5.0F, config.calculateReducedDamage(10.0F));

		config.fallDamageReduction = 0.95;
		assertEquals(0.5F, config.calculateReducedDamage(10.0F), 0.0001F);
	}

	@Test
	void calculatesDurabilityCostFromPreventedDamage() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		config.durabilityCostMultiplier = 0.0;
		assertEquals(0, config.calculateDurabilityCost(4.0F));

		config.durabilityCostMultiplier = 0.1;
		assertEquals(1, config.calculateDurabilityCost(0.1F));

		config.durabilityCostMultiplier = 2.5;
		assertEquals(5, config.calculateDurabilityCost(2.0F));

		assertEquals(0, config.calculateDurabilityCost(0.0F));
	}

	@Test
	void checksConfiguredShieldRaiseTime() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.requireShieldRaiseTime = true;
		config.minShieldRaiseTicks = 10;

		assertFalse(config.isRaiseTimeSatisfied(9));
		assertTrue(config.isRaiseTimeSatisfied(10));
		assertTrue(config.isRaiseTimeSatisfied(11));

		config.requireShieldRaiseTime = false;
		assertTrue(config.isRaiseTimeSatisfied(0));
	}

	@Test
	void selectsShieldChargeCooldownForCounterLevel() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(40, config.getShieldChargeCooldownTicks(1));
		assertEquals(50, config.getShieldChargeCooldownTicks(2));
		assertEquals(60, config.getShieldChargeCooldownTicks(3));
		assertEquals(60, config.getShieldChargeCooldownTicks(99));
		assertEquals(0, config.getShieldChargeCooldownTicks(0));

		config.enableShieldChargeCooldown = false;

		assertEquals(0, config.getShieldChargeCooldownTicks(3));
	}

	@Test
	void selectsEnergyCounterValuesForLevel() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(20.0, config.getEnergyCounterThreshold(1));
		assertEquals(16.0, config.getEnergyCounterThreshold(2));
		assertEquals(12.0, config.getEnergyCounterThreshold(3));
		assertEquals(12.0, config.getEnergyCounterThreshold(99));
		assertEquals(0.0, config.getEnergyCounterThreshold(0));
		assertEquals(1.5, config.getEnergyCounterMultiplier(1));
		assertEquals(2.0, config.getEnergyCounterMultiplier(2));
		assertEquals(3.0, config.getEnergyCounterMultiplier(3));
		assertEquals(30.0, config.getEnergyCounterMaxStoredDamage(1));
		assertEquals(40.0, config.getEnergyCounterMaxStoredDamage(2));
		assertEquals(50.0, config.getEnergyCounterMaxStoredDamage(3));
		assertEquals(80, config.getEnergyCounterCooldownTicks(1));
		assertEquals(100, config.getEnergyCounterCooldownTicks(2));
		assertEquals(120, config.getEnergyCounterCooldownTicks(3));

		config.enableEnergyCounter = false;

		assertEquals(0.0, config.getEnergyCounterThreshold(3));
		assertEquals(0, config.getEnergyCounterCooldownTicks(3));
	}
}
