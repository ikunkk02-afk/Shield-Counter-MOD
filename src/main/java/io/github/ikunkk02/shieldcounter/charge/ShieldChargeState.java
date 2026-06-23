package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import net.minecraft.nbt.NbtCompound;

final class ShieldChargeState {
	public static final String NBT_KEY = "shieldChargeTicks";

	private int chargeTicks;
	private int chargeLevel;

	public int getChargeTicks() {
		return this.chargeTicks;
	}

	public int update(boolean charging, ShieldCounterConfig config) {
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

	public void writeToNbt(NbtCompound nbt) {
		nbt.putInt(NBT_KEY, this.chargeTicks);
	}

	public void readFromNbt(NbtCompound nbt, ShieldCounterConfig config) {
		this.chargeTicks = Math.clamp(nbt.getInt(NBT_KEY), 0, config.maxShieldChargeTicks);
		this.chargeLevel = config.getShieldChargeLevel(this.chargeTicks);
	}
}
