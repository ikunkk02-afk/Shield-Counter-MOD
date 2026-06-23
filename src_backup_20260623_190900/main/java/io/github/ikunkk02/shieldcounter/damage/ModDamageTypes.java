package io.github.ikunkk02.shieldcounter.damage;

import net.minecraft.entity.damage.DamageType;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.util.Identifier;

import static io.github.ikunkk02.shieldcounter.ShieldCounter.MOD_ID;

public final class ModDamageTypes {
    public static final Identifier SHIELD_REFLECT_ID = Identifier.of(MOD_ID, "shield_reflect");
    public static final net.minecraft.registry.RegistryKey<DamageType> SHIELD_REFLECT =
            net.minecraft.registry.RegistryKey.of(RegistryKeys.DAMAGE_TYPE, SHIELD_REFLECT_ID);

    private ModDamageTypes() {
    }
}
