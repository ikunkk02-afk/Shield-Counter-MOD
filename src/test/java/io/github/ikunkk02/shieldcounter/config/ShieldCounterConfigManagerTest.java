package io.github.ikunkk02.shieldcounter.config;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertTrue;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.io.TempDir;

class ShieldCounterConfigManagerTest {
	@TempDir
	Path tempDir;

	@Test
	void missingFileCreatesAndActivatesDefaults() throws IOException {
		Path configPath = tempDir.resolve("shield_counter.json");

		ShieldCounterConfigManager.load(configPath);

		assertTrue(Files.isRegularFile(configPath));
		assertEquals(0.5, ShieldCounterConfigManager.get().fallDamageReduction);
		assertTrue(Files.readString(configPath).contains("\"fallDamageReduction\": 0.5"));
	}

	@Test
	void validFileLoadsValidatedValues() throws IOException {
		Path configPath = tempDir.resolve("shield_counter.json");
		Files.writeString(configPath, """
			{
			  "enableFallShieldBlock": false,
			  "fallDamageReduction": 0.75,
			  "durabilityCostMultiplier": 2.0,
			  "requireShieldRaiseTime": false,
			  "minShieldRaiseTicks": 30
			}
			""");

		ShieldCounterConfigManager.load(configPath);
		ShieldCounterConfig config = ShieldCounterConfigManager.get();

		assertEquals(false, config.enableFallShieldBlock);
		assertEquals(0.75, config.fallDamageReduction);
		assertEquals(2.0, config.durabilityCostMultiplier);
		assertEquals(false, config.requireShieldRaiseTime);
		assertEquals(30, config.minShieldRaiseTicks);
	}

	@Test
	void malformedFileFallsBackToDefaultsAndIsRewritten() throws IOException {
		Path configPath = tempDir.resolve("shield_counter.json");
		Files.writeString(configPath, "{ definitely not valid json");

		ShieldCounterConfigManager.load(configPath);

		assertEquals(0.5, ShieldCounterConfigManager.get().fallDamageReduction);
		String rewritten = Files.readString(configPath);
		assertTrue(rewritten.startsWith("{"));
		assertTrue(rewritten.contains("\"enableFallShieldBlock\": true"));
	}

	@Test
	void successfulSaveValidatesPersistsAndActivates() throws IOException {
		Path configPath = tempDir.resolve("nested").resolve("shield_counter.json");
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.fallDamageReduction = 5.0;
		config.minShieldRaiseTicks = -1;

		assertTrue(ShieldCounterConfigManager.save(config, configPath));

		assertEquals(0.95, ShieldCounterConfigManager.get().fallDamageReduction);
		assertEquals(0, ShieldCounterConfigManager.get().minShieldRaiseTicks);
		assertTrue(Files.readString(configPath).contains("\"fallDamageReduction\": 0.95"));
	}
}
