package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import net.minecraft.nbt.NbtCompound;

final class ShieldChargeState {
	public static final String NBT_KEY = "shieldChargeTicks";
	public static final String COOLDOWN_NBT_KEY = "shieldChargeCooldownTicks";
	public static final String COOLDOWN_DURATION_NBT_KEY = "shieldChargeCooldownDurationTicks";
	public static final String STORED_SHIELD_DAMAGE_NBT_KEY = "storedShieldDamage";
	public static final String ENERGY_COUNTER_COOLDOWN_NBT_KEY = "energyCounterCooldownTicks";

	private int chargeTicks;
	private int chargeLevel;
	private int cooldownTicks;
	private int cooldownDurationTicks;
	private float storedShieldDamage;
	private int energyCounterCooldownTicks;

	public int getChargeTicks() {
		return this.chargeTicks;
	}

	public int getCooldownTicks() {
		return this.cooldownTicks;
	}

	public int getCooldownDurationTicks() {
		return this.cooldownDurationTicks;
	}

	public boolean isOnCooldown() {
		return this.cooldownTicks > 0;
	}

	public float getStoredShieldDamage() {
		return this.storedShieldDamage;
	}

	public int getEnergyCounterCooldownTicks() {
		return this.energyCounterCooldownTicks;
	}

	public boolean isEnergyCounterOnCooldown() {
		return this.energyCounterCooldownTicks > 0;
	}

	public int update(boolean charging, ShieldCounterConfig config) {
		if (this.energyCounterCooldownTicks > 0) {
			this.energyCounterCooldownTicks--;
		}

		if (this.cooldownTicks > 0) {
			this.cooldownTicks--;
			if (this.cooldownTicks == 0) {
				this.cooldownDurationTicks = 0;
			}
			this.reset();
			return 0;
		}

		if (!config.enableShieldCharge || !charging) {
			this.reset();
			return 0;
		}

		this.chargeTicks = Math.min(this.chargeTicks + 1, config.maxShieldChargeTicks);
		int currentLevel = config.getShieldChargeLevel(this.chargeTicks);
		boolean enteredHigherStage = currentLevel > this.chargeLevel;
		this.chargeLevel = currentLevel;
		return enteredHigherStage ? currentLevel : 0;
	}

	public void reset() {
		this.chargeTicks = 0;
		this.chargeLevel = 0;
	}

	public void addStoredShieldDamage(float amount, float maxStoredDamage) {
		if (this.energyCounterCooldownTicks > 0 || amount <= 0.0F || maxStoredDamage <= 0.0F) {
			return;
		}

		this.storedShieldDamage = Math.clamp(
			this.storedShieldDamage + amount,
			0.0F,
			maxStoredDamage
		);
	}

	public void resetStoredShieldDamage() {
		this.storedShieldDamage = 0.0F;
	}

	public void setEnergyCounterCooldown(int ticks) {
		int boundedTicks = Math.clamp(
			ticks,
			ShieldCounterConfig.MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			ShieldCounterConfig.MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
		this.energyCounterCooldownTicks = boundedTicks;
		if (boundedTicks > 0) {
			this.resetStoredShieldDamage();
		}
	}

	public void resetEnergyCounterCooldown() {
		this.energyCounterCooldownTicks = 0;
	}

	public int consumeForCounter(int enchantmentLevel, ShieldCounterConfig config) {
		if (this.cooldownTicks > 0 || !config.enableShieldCharge) {
			this.reset();
			return 0;
		}

		int effectiveChargeLevel = Math.clamp(
			config.getShieldChargeLevel(this.chargeTicks),
			0,
			3
		);
		boolean shouldConsumeCharge = config.consumeChargeOnCounter || effectiveChargeLevel >= 3;
		if (shouldConsumeCharge) {
			this.reset();
		}
		if (effectiveChargeLevel >= 3) {
			this.setCooldown(config.getShieldChargeCooldownTicks(enchantmentLevel));
		}
		return effectiveChargeLevel;
	}

	public void setCooldown(int ticks) {
		int boundedTicks = Math.clamp(
			ticks,
			ShieldCounterConfig.MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			ShieldCounterConfig.MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		this.cooldownTicks = boundedTicks;
		this.cooldownDurationTicks = boundedTicks;
	}

	public void resetCooldown() {
		this.cooldownTicks = 0;
		this.cooldownDurationTicks = 0;
	}

	public void resetAll() {
		this.reset();
		this.resetCooldown();
		this.resetStoredShieldDamage();
		this.resetEnergyCounterCooldown();
	}

	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt(NBT_KEY, this.chargeTicks);
		nbt.putInt(COOLDOWN_NBT_KEY, this.cooldownTicks);
		nbt.putInt(COOLDOWN_DURATION_NBT_KEY, this.cooldownDurationTicks);
		nbt.putFloat(STORED_SHIELD_DAMAGE_NBT_KEY, this.storedShieldDamage);
		nbt.putInt(ENERGY_COUNTER_COOLDOWN_NBT_KEY, this.energyCounterCooldownTicks);
	}

	public void readFromNbt(NbtCompound nbt, ShieldCounterConfig config) {
		this.chargeTicks = Math.clamp(nbt.getInt(NBT_KEY), 0, config.maxShieldChargeTicks);
		this.chargeLevel = config.getShieldChargeLevel(this.chargeTicks);
		this.cooldownTicks = Math.clamp(
			nbt.getInt(COOLDOWN_NBT_KEY),
			ShieldCounterConfig.MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			ShieldCounterConfig.MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		this.cooldownDurationTicks = Math.clamp(
			nbt.getInt(COOLDOWN_DURATION_NBT_KEY),
			ShieldCounterConfig.MIN_SHIELD_CHARGE_COOLDOWN_TICKS,
			ShieldCounterConfig.MAX_SHIELD_CHARGE_COOLDOWN_TICKS
		);
		if (this.cooldownTicks == 0) {
			this.cooldownDurationTicks = 0;
		} else if (this.cooldownDurationTicks < this.cooldownTicks) {
			this.cooldownDurationTicks = this.cooldownTicks;
		}
		if (this.cooldownTicks > 0) {
			this.reset();
		}
		this.energyCounterCooldownTicks = Math.clamp(
			nbt.getInt(ENERGY_COUNTER_COOLDOWN_NBT_KEY),
			ShieldCounterConfig.MIN_ENERGY_COUNTER_COOLDOWN_TICKS,
			ShieldCounterConfig.MAX_ENERGY_COUNTER_COOLDOWN_TICKS
		);
		float maxStoredDamage = (float) config.getEnergyCounterMaxStoredDamage(3);
		this.storedShieldDamage = Math.clamp(
			nbt.getFloat(STORED_SHIELD_DAMAGE_NBT_KEY),
			0.0F,
			maxStoredDamage
		);
		if (this.energyCounterCooldownTicks > 0) {
			this.resetStoredShieldDamage();
		}
	}
}
