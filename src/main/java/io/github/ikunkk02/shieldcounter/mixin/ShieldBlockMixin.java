package io.github.ikunkk02.shieldcounter.mixin;

import io.github.ikunkk02.shieldcounter.charge.ShieldChargeApi;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import io.github.ikunkk02.shieldcounter.counter.EnergyCounterRules;
import io.github.ikunkk02.shieldcounter.counter.PendingShieldCounter;
import io.github.ikunkk02.shieldcounter.counter.ShieldCounterPlayerAccess;
import io.github.ikunkk02.shieldcounter.counter.ShieldCounterRules;
import io.github.ikunkk02.shieldcounter.damage.ModDamageTypes;
import io.github.ikunkk02.shieldcounter.enchantment.ModEnchantments;
import io.github.ikunkk02.shieldcounter.sound.ModSounds;
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
import net.minecraft.text.Text;
import net.minecraft.util.math.Vec3d;
import java.util.Locale;
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
		RegistryEntry<Enchantment> energyCounter = ModEnchantments.getEnergyCounterEntry(player.getWorld());
		int energyCounterLevel = energyCounter == null
			? 0
			: EnchantmentHelper.getLevel(energyCounter, shieldStack);
		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		boolean usingShield = player.isUsingItem() && shieldStack.isOf(Items.SHIELD);
		boolean reflectedDamage = source.isOf(ModDamageTypes.SHIELD_REFLECT);
		boolean shouldCounter = ShieldCounterRules.shouldCounter(
			config.enableShieldCounter,
			true,
			usingShield,
			enchantmentLevel,
			amount,
			true,
			reflectedDamage
		);
		boolean shouldProcessEnergyCounter = EnergyCounterRules.shouldProcess(
			config.enableEnergyCounter,
			true,
			usingShield,
			energyCounterLevel,
			amount,
			true,
			reflectedDamage,
			ShieldChargeApi.isEnergyCounterOnCooldown(player)
		);
		if (!shouldCounter && !shouldProcessEnergyCounter) {
			return;
		}

		int chargeLevel = 0;
		double reflectRatio = 0.0;
		double knockback = 0.0;
		double durabilityMultiplier = 1.0;
		if (shouldCounter) {
			chargeLevel = ShieldChargeApi.consumeShieldChargeForCounter(
				player,
				enchantmentLevel,
				config
			);
			reflectRatio = ShieldCounterRules.calculateReflectRatio(enchantmentLevel, chargeLevel, config);
			knockback = ShieldCounterRules.calculateKnockback(enchantmentLevel, chargeLevel, config);
			durabilityMultiplier = config.counterDurabilityCostMultiplier;
		}

		float energyReleaseDamage = 0.0F;
		double energyReleaseKnockback = 0.0D;
		int energyDurabilityCost = 0;
		if (shouldProcessEnergyCounter) {
			float storedDamage = ShieldChargeApi.getStoredShieldDamage(player);
			if (EnergyCounterRules.shouldRelease(storedDamage, energyCounterLevel, config)) {
				energyReleaseDamage = EnergyCounterRules.calculateReleaseDamage(
					storedDamage,
					energyCounterLevel,
					config
				);
				energyReleaseKnockback = EnergyCounterRules.calculateReleaseKnockback(energyCounterLevel);
				energyDurabilityCost = EnergyCounterRules.calculateReleaseDurabilityCost(energyReleaseDamage);
			} else {
				float maxStoredDamage = EnergyCounterRules.getMaxStoredDamage(energyCounterLevel, config);
				ShieldChargeApi.addStoredShieldDamage(player, amount, maxStoredDamage);
				shieldCounter$sendEnergyStoredMessage(player, energyCounterLevel, config);
			}
		}

		if (!shouldCounter && energyReleaseDamage <= 0.0F) {
			return;
		}

		PendingShieldCounter pendingCounter = new PendingShieldCounter(
			attacker,
			amount,
			enchantmentLevel,
			chargeLevel,
			reflectRatio,
			knockback,
			energyCounterLevel,
			energyReleaseDamage,
			energyReleaseKnockback,
			energyDurabilityCost,
			0,
			durabilityMultiplier,
			false
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

		if (pendingCounter.chargeCooldownTicks() > 0) {
			ShieldChargeApi.setShieldChargeCooldown(player, pendingCounter.chargeCooldownTicks());
		}

		if (pendingCounter.enchantmentLevel() > 0) {
			serverWorld.playSound(
				null,
				player.getX(),
				player.getY(),
				player.getZ(),
				ModSounds.COUNTER_TRIGGER,
				SoundCategory.PLAYERS,
				1.0F,
				1.0F
			);
			shieldCounter$applyChargedCounterFeedback(serverWorld, player, pendingCounter);

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

		if (pendingCounter.energyReleaseDamage() > 0.0F) {
			shieldCounter$applyEnergyRelease(serverWorld, player, pendingCounter);
		}
	}

	@Unique
	private static void shieldCounter$applyChargedCounterFeedback(
		ServerWorld serverWorld,
		PlayerEntity player,
		PendingShieldCounter pendingCounter
	) {
		int chargeLevel = pendingCounter.chargeLevel();
		if (chargeLevel <= 0) {
			return;
		}

		boolean fullCharge = chargeLevel >= 3;
		serverWorld.spawnParticles(
			fullCharge ? ParticleTypes.END_ROD : ParticleTypes.ENCHANTED_HIT,
			player.getX(),
			player.getBodyY(0.55D),
			player.getZ(),
			fullCharge ? 14 : 5 + chargeLevel * 2,
			0.35D,
			0.45D,
			0.35D,
			fullCharge ? 0.04D : 0.02D
		);

		if (fullCharge) {
			serverWorld.spawnParticles(
				ParticleTypes.ENCHANT,
				player.getX(),
				player.getBodyY(0.5D),
				player.getZ(),
				12,
				0.4D,
				0.5D,
				0.4D,
				0.05D
			);
		}

		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		if (!config.showShieldChargeStatusMessage) {
			return;
		}

		int reflectPercent = (int) Math.round(pendingCounter.reflectRatio() * 100.0D);
		if (fullCharge) {
			player.sendMessage(Text.translatable(
				"message.shield-counter.shield_charge_counter_full",
				reflectPercent,
				ShieldChargeApi.getShieldChargeCooldownTicks(player)
			), true);
		} else {
			player.sendMessage(Text.translatable(
				"message.shield-counter.shield_charge_counter",
				chargeLevel,
				reflectPercent
			), true);
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
	private static void shieldCounter$applyEnergyRelease(
		ServerWorld serverWorld,
		PlayerEntity player,
		PendingShieldCounter pendingCounter
	) {
		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		ShieldChargeApi.resetStoredShieldDamage(player);
		ShieldChargeApi.setEnergyCounterCooldown(
			player,
			EnergyCounterRules.getCooldownTicks(pendingCounter.energyCounterLevel(), config)
		);
		pendingCounter.attacker().damage(
			shieldCounter$createReflectSource(serverWorld, player),
			pendingCounter.energyReleaseDamage()
		);

		ItemStack shieldStack = player.getActiveItem();
		if (shieldStack.isOf(Items.SHIELD) && pendingCounter.energyDurabilityCost() > 0) {
			shieldStack.damage(
				pendingCounter.energyDurabilityCost(),
				player,
				LivingEntity.getSlotForHand(player.getActiveHand())
			);
		}

		boolean burst = pendingCounter.energyCounterLevel() >= 3;
		serverWorld.playSound(
			null,
			player.getX(),
			player.getY(),
			player.getZ(),
			ModSounds.ENERGY_COUNTER_TRIGGER,
			SoundCategory.PLAYERS,
			1.0F,
			1.0F
		);

		if (config.showEnergyCounterMessage) {
			player.sendMessage(Text.translatable(
				burst
					? "message.shield-counter.energy_counter_burst"
					: "message.shield-counter.energy_counter_release"
			), true);
		}

		if (burst) {
			shieldCounter$applyKnockback(
				player,
				pendingCounter.attacker(),
				pendingCounter.energyReleaseKnockback()
			);
			serverWorld.spawnParticles(
				ParticleTypes.EXPLOSION,
				pendingCounter.attacker().getX(),
				pendingCounter.attacker().getBodyY(0.5),
				pendingCounter.attacker().getZ(),
				3,
				0.25,
				0.35,
				0.25,
				0.02
			);
			serverWorld.spawnParticles(
				ParticleTypes.CRIT,
				pendingCounter.attacker().getX(),
				pendingCounter.attacker().getBodyY(0.5),
				pendingCounter.attacker().getZ(),
				18,
				0.35,
				0.45,
				0.35,
				0.08
			);
		}
	}

	@Unique
	private static void shieldCounter$sendEnergyStoredMessage(
		PlayerEntity player,
		int energyCounterLevel,
		ShieldCounterConfig config
	) {
		if (!config.showEnergyCounterMessage) {
			return;
		}
		float storedDamage = ShieldChargeApi.getStoredShieldDamage(player);
		float threshold = EnergyCounterRules.getThreshold(energyCounterLevel, config);
		player.sendMessage(Text.translatable(
			"message.shield-counter.energy_counter_store",
			shieldCounter$formatOneDecimal(storedDamage),
			shieldCounter$formatOneDecimal(threshold)
		), true);
	}

	@Unique
	private static String shieldCounter$formatOneDecimal(float value) {
		return String.format(Locale.ROOT, "%.1f", value);
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
