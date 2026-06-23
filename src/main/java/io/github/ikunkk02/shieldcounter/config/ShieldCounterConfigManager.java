package io.github.ikunkk02.shieldcounter.config;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import io.github.ikunkk02.shieldcounter.ShieldCounter;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.nio.file.Files;
import java.nio.file.Path;
import net.fabricmc.loader.api.FabricLoader;

public final class ShieldCounterConfigManager {
	public static final String CONFIG_FILE_NAME = "shield_counter.json";

	private static final Gson GSON = new GsonBuilder().setPrettyPrinting().create();
	private static volatile ShieldCounterConfig current = new ShieldCounterConfig();

	private ShieldCounterConfigManager() {
	}

	public static ShieldCounterConfig get() {
		return current;
	}

	public static void load() {
		load(FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME));
	}

	static void load(Path configPath) {
		if (!Files.isRegularFile(configPath)) {
			ShieldCounterConfig defaults = new ShieldCounterConfig();
			current = defaults;
			writeConfig(defaults, configPath);
			return;
		}

		try {
			String json = Files.readString(configPath, StandardCharsets.UTF_8);
			ShieldCounterConfig loaded = GSON.fromJson(json, ShieldCounterConfig.class);
			if (loaded == null) {
				throw new IOException("Configuration root cannot be null");
			}

			current = loaded.validatedCopy();
		} catch (Exception exception) {
			ShieldCounter.LOGGER.error("Failed to load {}, restoring defaults", configPath, exception);
			ShieldCounterConfig defaults = new ShieldCounterConfig();
			current = defaults;
			writeConfig(defaults, configPath);
		}
	}

	public static boolean save(ShieldCounterConfig config) {
		return save(config, FabricLoader.getInstance().getConfigDir().resolve(CONFIG_FILE_NAME));
	}

	static boolean save(ShieldCounterConfig config, Path configPath) {
		ShieldCounterConfig validated = config.validatedCopy();
		if (!writeConfig(validated, configPath)) {
			return false;
		}

		current = validated;
		return true;
	}

	private static boolean writeConfig(ShieldCounterConfig config, Path configPath) {
		try {
			Path parent = configPath.getParent();
			if (parent != null) {
				Files.createDirectories(parent);
			}
			Files.writeString(configPath, GSON.toJson(config) + System.lineSeparator(), StandardCharsets.UTF_8);
			return true;
		} catch (IOException exception) {
			ShieldCounter.LOGGER.error("Failed to save {}", configPath, exception);
			return false;
		}
	}
}
