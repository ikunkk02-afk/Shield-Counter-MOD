package io.github.ikunkk02.shieldcounter.client;

import io.github.ikunkk02.shieldcounter.client.hud.ShieldChargeHud;
import net.fabricmc.api.ClientModInitializer;

public class ShieldCounterClient implements ClientModInitializer {
	@Override
	public void onInitializeClient() {
		ShieldChargeHud.register();
	}
}