package io.github.ikunkk02.shieldcounter.counter;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.junit.jupiter.api.Assertions.assertFalse;
import static org.junit.jupiter.api.Assertions.assertTrue;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import org.junit.jupiter.api.Test;

class ShieldCounterResourcesTest {
	private static final Path RESOURCES = Path.of("src", "main", "resources");

	@Test
	void enchantmentIsAThreeLevelShieldOnlyDataDrivenEnchantment() throws IOException {
		JsonObject enchantment = readJson(
			RESOURCES.resolve("data/shield-counter/enchantment/shield_counter.json")
		);

		assertEquals(3, enchantment.get("max_level").getAsInt());
		assertEquals("#shield-counter:enchantable/shield", enchantment.get("supported_items").getAsString());
		assertEquals("#shield-counter:enchantable/shield", enchantment.get("primary_items").getAsString());
		assertTrue(enchantment.getAsJsonArray("slots").contains(JsonParser.parseString("\"hand\"")));
	}

	@Test
	void enchantmentIsAvailableThroughTheNormalNonTreasurePools() throws IOException {
		JsonObject tag = readJson(
			RESOURCES.resolve("data/minecraft/tags/enchantment/non_treasure.json")
		);

		assertTrue(tag.getAsJsonArray("values").contains(
			JsonParser.parseString("\"shield-counter:shield_counter\"")
		));
	}

	@Test
	void reflectedDamageBypassesShieldsAndUsesTheCorrectDamageTypeId() throws IOException {
		JsonObject tag = readJson(
			RESOURCES.resolve("data/minecraft/tags/damage_type/bypasses_shield.json")
		);
		JsonArray values = tag.getAsJsonArray("values");

		assertTrue(values.contains(JsonParser.parseString("\"shield-counter:shield_reflect\"")));
		assertFalse(values.contains(JsonParser.parseString("\"shield-counter:shield_counter\"")));

		JsonObject damageType = readJson(
			RESOURCES.resolve("data/shield-counter/damage_type/shield_reflect.json")
		);
		assertEquals("shield_reflect", damageType.get("message_id").getAsString());
	}

	@Test
	void shieldEnchantableTagContainsOnlyTheVanillaShield() throws IOException {
		JsonObject tag = readJson(
			RESOURCES.resolve("data/shield-counter/tags/item/enchantable/shield.json")
		);

		assertEquals(1, tag.getAsJsonArray("values").size());
		assertEquals("minecraft:shield", tag.getAsJsonArray("values").get(0).getAsString());
	}

	private static JsonObject readJson(Path path) throws IOException {
		return JsonParser.parseString(Files.readString(path)).getAsJsonObject();
	}
}
