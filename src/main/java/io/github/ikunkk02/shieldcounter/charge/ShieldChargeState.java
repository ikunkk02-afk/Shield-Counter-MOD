package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import net.minecraft.nbt.NbtCompound;

final class ShieldChargeState {
	public static final String NBT_KEY = "shieldChargeTicks";
	public static final String COOLDOWN_NBT_KEY = "shieldChargeCooldownTicks";
	public static final String COOLDOWN_DURATION_NBT_KEY = "shieldChargeCooldownDurationTicks";

	private int chargeTicks;
	private int chargeLevel;
	private int cooldownTicks;
	private int cooldownDurationTicks;

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

	public int update(boolean charging, ShieldCounterConfig config) {
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
	}

	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt(NBT_KEY, this.chargeTicks);
		nbt.putInt(COOLDOWN_NBT_KEY, this.cooldownTicks);
		nbt.putInt(COOLDOWN_DURATION_NBT_KEY, this.cooldownDurationTicks);
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
	}
}
