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
