package io.github.ikunkk02.shieldcounter.enchantment;

import net.minecraft.enchantment.Enchantment;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static io.github.ikunkk02.shieldcounter.ShieldCounter.MOD_ID;

public final class ModEnchantments {
    public static final Identifier SHIELD_COUNTER_ID = Identifier.of(MOD_ID, "shield_counter");
    public static final net.minecraft.registry.RegistryKey<Enchantment> SHIELD_COUNTER =
            net.minecraft.registry.RegistryKey.of(RegistryKeys.ENCHANTMENT, SHIELD_COUNTER_ID);

    private ModEnchantments() {
    }

    public static RegistryEntry<Enchantment> getEntry(World world) {
        return world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(SHIELD_COUNTER)
                .orElse(null);
    }
}
