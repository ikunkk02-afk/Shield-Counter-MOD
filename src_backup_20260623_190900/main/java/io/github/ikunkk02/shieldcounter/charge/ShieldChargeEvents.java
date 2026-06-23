package io.github.ikunkk02.shieldcounter.charge;

import net.fabricmc.fabric.api.entity.event.v1.ServerLivingEntityEvents;
import net.fabricmc.fabric.api.networking.v1.ServerPlayConnectionEvents;
import net.minecraft.entity.player.PlayerEntity;

public final class ShieldChargeEvents {
	private ShieldChargeEvents() {
	}

	public static void register() {
		ServerLivingEntityEvents.AFTER_DEATH.register((entity, damageSource) -> {
			if (entity instanceof PlayerEntity player) {
				ShieldChargeApi.resetShieldCharge(player);
			}
		});
		ServerPlayConnectionEvents.DISCONNECT.register((handler, server) ->
			ShieldChargeApi.resetShieldCharge(handler.getPlayer())
		);
	}
}
