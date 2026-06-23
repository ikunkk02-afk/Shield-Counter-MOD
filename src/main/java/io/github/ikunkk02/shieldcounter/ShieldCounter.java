package io.github.ikunkk02.shieldcounter;

import io.github.ikunkk02.shieldcounter.charge.ShieldChargeEvents;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.fabricmc.api.ModInitializer;
import net.minecraft.util.Identifier;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

public class ShieldCounter implements ModInitializer {
	public static final String MOD_ID = "shield-counter";

	// This logger is used to write text to the console and the log file.
	// It is considered best practice to use your mod id as the logger's name.
	// That way, it's clear which mod wrote info, warnings, and errors.
	public static final Logger LOGGER = LoggerFactory.getLogger(MOD_ID);

	@Override
	public void onInitialize() {
		ShieldCounterConfigManager.load();
		ShieldChargeEvents.register();
		LOGGER.info("Shield Counter initialized");
	}

	public static Identifier id(String path) {
		return Identifier.of(MOD_ID, path);
	}
}
