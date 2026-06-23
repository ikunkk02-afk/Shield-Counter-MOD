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

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(0.95, validated.fallDamageReduction);
		assertEquals(0.0, validated.durabilityCostMultiplier);
		assertEquals(60, validated.minShieldRaiseTicks);

		config.fallDamageReduction = -1.0;
		config.durabilityCostMultiplier = 10.0;
		config.minShieldRaiseTicks = -5;
		validated = config.validatedCopy();

		assertEquals(0.0, validated.fallDamageReduction);
		assertEquals(5.0, validated.durabilityCostMultiplier);
		assertEquals(0, validated.minShieldRaiseTicks);
	}

	@Test
	void validationRestoresDefaultsForNonFiniteNumbers() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.fallDamageReduction = Double.NaN;
		config.durabilityCostMultiplier = Double.POSITIVE_INFINITY;

		ShieldCounterConfig validated = config.validatedCopy();

		assertEquals(0.5, validated.fallDamageReduction);
		assertEquals(1.0, validated.durabilityCostMultiplier);
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
}
