package io.github.ikunkk02.shieldcounter.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import org.junit.jupiter.api.Test;

class EnergyCounterRulesTest {
	private static final double EPSILON = 0.000001;

	@Test
	void requiresEnabledServerSideLivingEntityShieldBlockWithEnergyCounter() {
		assertTrue(EnergyCounterRules.shouldProcess(true, true, true, 1, 6.0F, true, false, false));

		assertFalse(EnergyCounterRules.shouldProcess(false, true, true, 1, 6.0F, true, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, false, true, 1, 6.0F, true, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, false, 1, 6.0F, true, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, true, 0, 6.0F, true, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, true, 1, 0.0F, true, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, true, 1, 6.0F, false, false, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, true, 1, 6.0F, true, true, false));
		assertFalse(EnergyCounterRules.shouldProcess(true, true, true, 1, 6.0F, true, false, true));
	}

	@Test
	void storesDamageUntilTheNextBlockAfterThresholdIsReached() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertFalse(EnergyCounterRules.shouldRelease(19.0F, 1, config));
		assertEquals(20.0F, EnergyCounterRules.calculateStoredDamageAfterBlock(15.0F, 5.0F, 1, config));
		assertTrue(EnergyCounterRules.shouldRelease(20.0F, 1, config));
	}

	@Test
	void levelTuningMatchesTheDesign() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(20.0F, EnergyCounterRules.getThreshold(1, config));
		assertEquals(16.0F, EnergyCounterRules.getThreshold(2, config));
		assertEquals(12.0F, EnergyCounterRules.getThreshold(3, config));
		assertEquals(30.0F, EnergyCounterRules.getMaxStoredDamage(1, config));
		assertEquals(40.0F, EnergyCounterRules.getMaxStoredDamage(2, config));
		assertEquals(50.0F, EnergyCounterRules.getMaxStoredDamage(3, config));
		assertEquals(80, EnergyCounterRules.getCooldownTicks(1, config));
		assertEquals(100, EnergyCounterRules.getCooldownTicks(2, config));
		assertEquals(120, EnergyCounterRules.getCooldownTicks(3, config));
		assertEquals(1.5, EnergyCounterRules.getMultiplier(1, config), EPSILON);
		assertEquals(2.0, EnergyCounterRules.getMultiplier(2, config), EPSILON);
		assertEquals(3.0, EnergyCounterRules.getMultiplier(3, config), EPSILON);
	}

	@Test
	void releaseDamageUsesStoredDamageTimesLevelMultiplier() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(30.0F, EnergyCounterRules.calculateReleaseDamage(20.0F, 1, config));
		assertEquals(32.0F, EnergyCounterRules.calculateReleaseDamage(16.0F, 2, config));
		assertEquals(45.0F, EnergyCounterRules.calculateReleaseDamage(15.0F, 3, config));
	}

	@Test
	void storageIsClampedToTheLevelMaximum() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(30.0F, EnergyCounterRules.calculateStoredDamageAfterBlock(20.0F, 20.0F, 1, config));
		assertEquals(40.0F, EnergyCounterRules.calculateStoredDamageAfterBlock(35.0F, 20.0F, 2, config));
		assertEquals(50.0F, EnergyCounterRules.calculateStoredDamageAfterBlock(45.0F, 20.0F, 3, config));
	}

	@Test
	void levelThreeHasExtraKnockbackAndReleaseDurabilityCost() {
		assertEquals(0.0, EnergyCounterRules.calculateReleaseKnockback(1), EPSILON);
		assertEquals(0.0, EnergyCounterRules.calculateReleaseKnockback(2), EPSILON);
		assertTrue(EnergyCounterRules.calculateReleaseKnockback(3) > 0.0);

		assertEquals(1, EnergyCounterRules.calculateReleaseDurabilityCost(0.1F));
		assertEquals(12, EnergyCounterRules.calculateReleaseDurabilityCost(45.0F));
	}
}
