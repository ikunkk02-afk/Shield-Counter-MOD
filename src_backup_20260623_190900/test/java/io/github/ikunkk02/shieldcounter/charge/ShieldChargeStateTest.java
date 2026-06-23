package io.github.ikunkk02.shieldcounter.charge;

import static org.junit.jupiter.api.Assertions.assertEquals;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import net.minecraft.nbt.NbtCompound;
import org.junit.jupiter.api.Test;

class ShieldChargeStateTest {
	@Test
	void accumulatesChargeAndReportsEachStageOnce() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		ShieldChargeState state = new ShieldChargeState();

		for (int tick = 1; tick < 20; tick++) {
			assertEquals(0, state.update(true, config));
		}
		assertEquals(1, state.update(true, config));
		assertEquals(0, state.update(true, config));

		while (state.getChargeTicks() < 39) {
			assertEquals(0, state.update(true, config));
		}
		assertEquals(2, state.update(true, config));

		while (state.getChargeTicks() < 59) {
			assertEquals(0, state.update(true, config));
		}
		assertEquals(3, state.update(true, config));
		assertEquals(0, state.update(true, config));
	}

	@Test
	void chargeStopsAtConfiguredMaximum() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		config.maxShieldChargeTicks = 75;
		ShieldChargeState state = new ShieldChargeState();

		for (int tick = 0; tick < 100; tick++) {
			state.update(true, config);
		}

		assertEquals(75, state.getChargeTicks());
	}

	@Test
	void nonChargingAndDisabledConfigResetCharge() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		ShieldChargeState state = new ShieldChargeState();

		state.update(true, config);
		state.update(true, config);
		assertEquals(2, state.getChargeTicks());

		state.update(false, config);
		assertEquals(0, state.getChargeTicks());

		state.update(true, config);
		config.enableShieldCharge = false;
		state.update(true, config);
		assertEquals(0, state.getChargeTicks());
	}

	@Test
	void nbtRoundTripPreservesChargeWithinConfiguredMaximum() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		ShieldChargeState original = new ShieldChargeState();
		for (int tick = 0; tick < 42; tick++) {
			original.update(true, config);
		}

		NbtCompound nbt = new NbtCompound();
		original.writeToNbt(nbt);
		ShieldChargeState restored = new ShieldChargeState();
		restored.readFromNbt(nbt, config);

		assertEquals(42, restored.getChargeTicks());

		nbt.putInt(ShieldChargeState.NBT_KEY, 500);
		restored.readFromNbt(nbt, config);
		assertEquals(60, restored.getChargeTicks());
	}

	@Test
	void resetClearsChargeImmediately() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		ShieldChargeState state = new ShieldChargeState();
		state.update(true, config);

		state.reset();

		assertEquals(0, state.getChargeTicks());
	}

	@Test
	void configChangeThatSkipsStagesReportsOnlyTheFinalStage() {
		ShieldCounterConfig config = new ShieldCounterConfig();
		ShieldChargeState state = new ShieldChargeState();
		for (int tick = 0; tick < 25; tick++) {
			state.update(true, config);
		}

		config.chargeStageOneTicks = 5;
		config.chargeStageTwoTicks = 10;
		config.chargeStageThreeTicks = 15;

		assertEquals(3, state.update(true, config));
		assertEquals(0, state.update(true, config));
	}
}
