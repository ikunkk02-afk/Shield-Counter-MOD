package io.github.ikunkk02.shieldcounter.sound;

import io.github.ikunkk02.shieldcounter.ShieldCounter;
import net.minecraft.registry.Registries;
import net.minecraft.registry.Registry;
import net.minecraft.sound.SoundEvent;
import net.minecraft.util.Identifier;

public final class ModSounds {
	public static final Identifier COUNTER_TRIGGER_ID = ShieldCounter.id("counter_trigger");
	public static final SoundEvent COUNTER_TRIGGER = SoundEvent.of(COUNTER_TRIGGER_ID);
	public static final Identifier ENERGY_COUNTER_TRIGGER_ID = ShieldCounter.id("energy_counter_trigger");
	public static final SoundEvent ENERGY_COUNTER_TRIGGER = SoundEvent.of(ENERGY_COUNTER_TRIGGER_ID);

	private ModSounds() {
	}

	public static void register() {
		Registry.register(Registries.SOUND_EVENT, COUNTER_TRIGGER_ID, COUNTER_TRIGGER);
		Registry.register(Registries.SOUND_EVENT, ENERGY_COUNTER_TRIGGER_ID, ENERGY_COUNTER_TRIGGER);
	}
}
