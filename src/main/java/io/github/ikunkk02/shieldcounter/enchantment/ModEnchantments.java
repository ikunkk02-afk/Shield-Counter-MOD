package io.github.ikunkk02.shieldcounter.enchantment;

import java.util.ArrayList;
import java.util.List;
import net.fabricmc.fabric.api.itemgroup.v1.ItemGroupEvents;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentLevelEntry;
import net.minecraft.item.EnchantedBookItem;
import net.minecraft.item.ItemGroups;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.RegistryKey;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.util.Identifier;
import net.minecraft.world.World;

import static io.github.ikunkk02.shieldcounter.ShieldCounter.MOD_ID;

public final class ModEnchantments {
    public static final Identifier SHIELD_COUNTER_ID = Identifier.of(MOD_ID, "shield_counter");
    public static final RegistryKey<Enchantment> SHIELD_COUNTER =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, SHIELD_COUNTER_ID);
    public static final Identifier ENERGY_COUNTER_ID = Identifier.of(MOD_ID, "energy_counter");
    public static final RegistryKey<Enchantment> ENERGY_COUNTER =
            RegistryKey.of(RegistryKeys.ENCHANTMENT, ENERGY_COUNTER_ID);

    private ModEnchantments() {
    }

    public static void registerCreativeEntries() {
        ItemGroupEvents.modifyEntriesEvent(ItemGroups.COMBAT).register(entries ->
            entries.getContext()
                .lookup()
                .getOptionalWrapper(RegistryKeys.ENCHANTMENT)
                .flatMap(registry -> registry.getOptional(ENERGY_COUNTER))
                .ifPresent(enchantment -> entries.addAfter(
                    Items.SHIELD,
                    createEnergyCounterCreativeBookStacks(enchantment)
                ))
        );
    }

    public static List<CreativeBookEntry> getEnergyCounterCreativeBookEntries() {
        return List.of(
            new CreativeBookEntry(ENERGY_COUNTER, 1),
            new CreativeBookEntry(ENERGY_COUNTER, 2),
            new CreativeBookEntry(ENERGY_COUNTER, 3)
        );
    }

    public static RegistryEntry<Enchantment> getEntry(World world) {
        return world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(SHIELD_COUNTER)
                .orElse(null);
    }

    public static RegistryEntry<Enchantment> getEnergyCounterEntry(World world) {
        return world.getRegistryManager()
                .get(RegistryKeys.ENCHANTMENT)
                .getEntry(ENERGY_COUNTER)
                .orElse(null);
    }

    private static List<ItemStack> createEnergyCounterCreativeBookStacks(
        RegistryEntry<Enchantment> enchantment
    ) {
        List<ItemStack> stacks = new ArrayList<>();
        for (CreativeBookEntry entry : getEnergyCounterCreativeBookEntries()) {
            stacks.add(EnchantedBookItem.forEnchantment(
                new EnchantmentLevelEntry(enchantment, entry.level())
            ));
        }
        return stacks;
    }

    public record CreativeBookEntry(RegistryKey<Enchantment> enchantment, int level) {
    }
}
