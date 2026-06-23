package io.github.ikunkk02.shieldcounter.mixin;

import io.github.ikunkk02.shieldcounter.charge.ShieldChargeApi;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import io.github.ikunkk02.shieldcounter.counter.PendingShieldCounter;
import io.github.ikunkk02.shieldcounter.counter.ShieldCounterPlayerAccess;
import io.github.ikunkk02.shieldcounter.counter.ShieldCounterRules;
import io.github.ikunkk02.shieldcounter.damage.ModDamageTypes;
import io.github.ikunkk02.shieldcounter.enchantment.ModEnchantments;
import net.minecraft.enchantment.Enchantment;
import net.minecraft.enchantment.EnchantmentHelper;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.damage.DamageType;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryKeys;
import net.minecraft.registry.entry.RegistryEntry;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.math.Vec3d;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(LivingEntity.class)
public abstract class ShieldBlockMixin {
	@Unique
	private static final double MIN_DIRECTION_LENGTH_SQUARED = 1.0E-6;

	@Inject(
		method = "damage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;damageShield(F)V"
		)
	)
	private void shieldCounter$prepareCounter(
		DamageSource source,
		float amount,
		CallbackInfoReturnable<Boolean> cir
	) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (!(self instanceof PlayerEntity player)
			|| !(player.getWorld() instanceof ServerWorld)
			|| !(source.getAttacker() instanceof LivingEntity attacker)) {
			return;
		}

		ItemStack shieldStack = player.getActiveItem();
		RegistryEntry<Enchantment> enchantment = ModEnchantments.getEntry(player.getWorld());
		int enchantmentLevel = enchantment == null
			? 0
			: EnchantmentHelper.getLevel(enchantment, shieldStack);
		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		boolean usingShield = player.isUsingItem() && shieldStack.isOf(Items.SHIELD);
		boolean reflectedDamage = source.isOf(ModDamageTypes.SHIELD_REFLECT);
		if (!ShieldCounterRules.shouldCounter(
			config.enableShieldCounter,
			true,
			usingShield,
			enchantmentLevel,
			amount,
			true,
			reflectedDamage
		)) {
			return;
		}

		int chargeLevel = ShieldChargeApi.getShieldChargeLevel(player);
		PendingShieldCounter pendingCounter = new PendingShieldCounter(
			attacker,
			amount,
			enchantmentLevel,
			chargeLevel,
			ShieldCounterRules.calculateReflectRatio(enchantmentLevel, chargeLevel, config),
			ShieldCounterRules.calculateKnockback(enchantmentLevel, chargeLevel, config),
			config.counterDurabilityCostMultiplier,
			config.consumeChargeOnCounter
		);
		((ShieldCounterPlayerAccess) player).shieldCounter$prepare(pendingCounter);
	}

	@Inject(method = "damage", at = @At("RETURN"))
	private void shieldCounter$applyCounter(
		DamageSource source,
		float amount,
		CallbackInfoReturnable<Boolean> cir
	) {
		LivingEntity self = (LivingEntity) (Object) this;
		if (!(self instanceof PlayerEntity player)
			|| !(player.getWorld() instanceof ServerWorld serverWorld)) {
			return;
		}

		PendingShieldCounter pendingCounter =
			((ShieldCounterPlayerAccess) player).shieldCounter$consume();
		if (pendingCounter == null) {
			return;
		}

		float reflectedDamage = (float) (
			pendingCounter.originalDamage() * pendingCounter.reflectRatio()
		);
		if (reflectedDamage > 0.0F) {
			pendingCounter.attacker().damage(
				shieldCounter$createReflectSource(serverWorld, player),
				reflectedDamage
			);
		}

		if (pendingCounter.consumeCharge()) {
			ShieldChargeApi.resetShieldCharge(player);
		}

		serverWorld.playSound(
			null,
			player.getX(),
			player.getY(),
			player.getZ(),
			SoundEvents.BLOCK_ANVIL_LAND,
			SoundCategory.PLAYERS,
			0.4F,
			1.6F
		);

		if (pendingCounter.enchantmentLevel() >= 3) {
			shieldCounter$applyKnockback(player, pendingCounter.attacker(), pendingCounter.knockback());
			serverWorld.spawnParticles(
				ParticleTypes.CRIT,
				pendingCounter.attacker().getX(),
				pendingCounter.attacker().getBodyY(0.5),
				pendingCounter.attacker().getZ(),
				10,
				0.3,
				0.35,
				0.3,
				0.06
			);
		}
	}

	@Unique
	private static DamageSource shieldCounter$createReflectSource(
		ServerWorld world,
		PlayerEntity player
	) {
		RegistryEntry<DamageType> damageType = world.getRegistryManager()
			.get(RegistryKeys.DAMAGE_TYPE)
			.getEntry(ModDamageTypes.SHIELD_REFLECT)
			.orElseThrow();
		return new DamageSource(damageType, player);
	}

	@Unique
	private static void shieldCounter$applyKnockback(
		PlayerEntity player,
		LivingEntity attacker,
		double strength
	) {
		if (strength <= 0.0) {
			return;
		}

		double x = player.getX() - attacker.getX();
		double z = player.getZ() - attacker.getZ();
		if (x * x + z * z < MIN_DIRECTION_LENGTH_SQUARED) {
			Vec3d lookDirection = player.getRotationVec(1.0F);
			x = -lookDirection.x;
			z = -lookDirection.z;
			if (x * x + z * z < MIN_DIRECTION_LENGTH_SQUARED) {
				z = 1.0;
			}
		}
		attacker.takeKnockback(strength, x, z);
	}
}
