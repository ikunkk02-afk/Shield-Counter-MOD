package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.minecraft.entity.player.PlayerEntity;

public final class ShieldChargeApi {
	private ShieldChargeApi() {
	}

	public static int getShieldChargeTicks(PlayerEntity player) {
		return ShieldChargeComponents.SHIELD_CHARGE.get(player).getShieldChargeTicks();
	}

	public static int getShieldChargeLevel(PlayerEntity player) {
		return ShieldCounterConfigManager.get().getShieldChargeLevel(getShieldChargeTicks(player));
	}

	public static void resetShieldCharge(PlayerEntity player) {
		ShieldChargeComponents.SHIELD_CHARGE.get(player).resetShieldCharge();
	}
}
