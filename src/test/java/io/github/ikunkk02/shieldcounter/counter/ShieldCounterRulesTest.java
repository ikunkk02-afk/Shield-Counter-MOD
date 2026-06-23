package io.github.ikunkk02.shieldcounter.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import org.junit.jupiter.api.Test;

class ShieldCounterRulesTest {
	private static final double EPSILON = 0.000001;

	@Test
	void requiresAnEnabledServerSideBlockedEntityAttackWithAnEnchantedShield() {
		assertTrue(ShieldCounterRules.shouldCounter(true, true, true, 1, 6.0F, true, false));

		assertFalse(ShieldCounterRules.shouldCounter(false, true, true, 1, 6.0F, true, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, false, true, 1, 6.0F, true, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, true, false, 1, 6.0F, true, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, true, true, 0, 6.0F, true, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, true, true, 1, 0.0F, true, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, true, true, 1, 6.0F, false, false));
		assertFalse(ShieldCounterRules.shouldCounter(true, true, true, 1, 6.0F, true, true));
	}

	@Test
	void calculatesLevelOneRatiosForEveryChargeStage() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(0.25, ShieldCounterRules.calculateReflectRatio(1, 0, config));
		assertEquals(0.35, ShieldCounterRules.calculateReflectRatio(1, 1, config));
		assertEquals(0.45, ShieldCounterRules.calculateReflectRatio(1, 2, config));
		assertEquals(0.60, ShieldCounterRules.calculateReflectRatio(1, 3, config));
	}

	@Test
	void calculatesLevelTwoRatiosForEveryChargeStage() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(0.50, ShieldCounterRules.calculateReflectRatio(2, 0, config));
		assertEquals(0.65, ShieldCounterRules.calculateReflectRatio(2, 1, config));
		assertEquals(0.80, ShieldCounterRules.calculateReflectRatio(2, 2, config));
		assertEquals(0.90, ShieldCounterRules.calculateReflectRatio(2, 3, config));
	}

	@Test
	void levelThreeAlwaysUsesTheConfiguredFullReflectRatio() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.counterLevel3BaseRatio = 0.8;

		for (int chargeLevel = 0; chargeLevel <= 3; chargeLevel++) {
			assertEquals(0.8, ShieldCounterRules.calculateReflectRatio(3, chargeLevel, config));
		}
	}

	@Test
	void configuredBaseRatiosRemainBounded() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.counterLevel1BaseRatio = 0.9;
		config.counterLevel2BaseRatio = -0.2;

		assertEquals(1.0, ShieldCounterRules.calculateReflectRatio(1, 3, config));
		assertEquals(0.0, ShieldCounterRules.calculateReflectRatio(2, 0, config));
		assertEquals(0.4, ShieldCounterRules.calculateReflectRatio(2, 3, config));
		assertEquals(0.0, ShieldCounterRules.calculateReflectRatio(0, 3, config));
	}

	@Test
	void levelThreeKnockbackUsesTheDocumentedStageWeights() {
		ShieldCounterConfig config = new ShieldCounterConfig();

		assertEquals(0.6, ShieldCounterRules.calculateKnockback(3, 0, config), EPSILON);
		assertEquals(0.9, ShieldCounterRules.calculateKnockback(3, 1, config), EPSILON);
		assertEquals(1.2, ShieldCounterRules.calculateKnockback(3, 2, config), EPSILON);
		assertEquals(1.6, ShieldCounterRules.calculateKnockback(3, 3, config), EPSILON);
		assertEquals(0.0, ShieldCounterRules.calculateKnockback(2, 3, config), EPSILON);
	}

	@Test
	void scalesTheVanillaIntegerDurabilityCost() {
		assertEquals(0, ShieldCounterRules.scaleDurabilityCost(4, 0.0));
		assertEquals(4, ShieldCounterRules.scaleDurabilityCost(4, 1.0));
		assertEquals(8, ShieldCounterRules.scaleDurabilityCost(4, 2.0));
		assertEquals(3, ShieldCounterRules.scaleDurabilityCost(4, 0.6));
		assertEquals(20, ShieldCounterRules.scaleDurabilityCost(4, 5.0));
		assertEquals(0, ShieldCounterRules.scaleDurabilityCost(0, 5.0));
	}
}
