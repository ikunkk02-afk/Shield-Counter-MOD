package io.github.ikunkk02.shieldcounter.enchantment;

import static org.junit.jupiter.api.Assertions.assertEquals;

import java.util.List;
import org.junit.jupiter.api.Test;

class ModEnchantmentsTest {
	@Test
	void energyCounterCreativeBooksIncludeAllThreeLevels() {
		assertEquals(List.of(
			new ModEnchantments.CreativeBookEntry(ModEnchantments.ENERGY_COUNTER, 1),
			new ModEnchantments.CreativeBookEntry(ModEnchantments.ENERGY_COUNTER, 2),
			new ModEnchantments.CreativeBookEntry(ModEnchantments.ENERGY_COUNTER, 3)
		), ModEnchantments.getEnergyCounterCreativeBookEntries());
	}
}
