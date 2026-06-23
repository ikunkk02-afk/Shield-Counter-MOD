package io.github.ikunkk02.shieldcounter.counter;

import net.minecraft.entity.LivingEntity;

public record PendingShieldCounter(
	LivingEntity attacker,
	float originalDamage,
	int enchantmentLevel,
	int chargeLevel,
	double reflectRatio,
	double knockback,
	double durabilityMultiplier,
	boolean consumeCharge
) {
}
