package io.github.ikunkk02.shieldcounter.client.hud;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.fabricmc.fabric.api.client.event.lifecycle.v1.ClientTickEvents;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;

import net.minecraft.client.render.RenderTickCounter;

public final class ShieldChargeHud {
	private static int clientChargeTicks;

	private ShieldChargeHud() {
	}

	public static void register() {
		ClientTickEvents.END_CLIENT_TICK.register(ShieldChargeHud::onClientTick);
		HudRenderCallback.EVENT.register(ShieldChargeHud::onHudRender);
	}

	private static void onClientTick(MinecraftClient client) {
		if (client.player == null) {
			clientChargeTicks = 0;
			return;
		}

		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		boolean usingShield = client.player.isUsingItem()
			&& client.player.getActiveItem().isOf(Items.SHIELD);

		if (config.enableShieldCharge && usingShield) {
			clientChargeTicks = Math.min(clientChargeTicks + 1, config.maxShieldChargeTicks);
		} else {
			clientChargeTicks = 0;
		}
	}

	private static void onHudRender(DrawContext context, RenderTickCounter tickCounter) {
		MinecraftClient client = MinecraftClient.getInstance();
		if (client.player == null) {
			return;
		}

		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		if (!config.enableShieldChargeHud) {
			return;
		}

		boolean usingShield = client.player.isUsingItem()
			&& client.player.getActiveItem().isOf(Items.SHIELD);
		if (!usingShield) {
			return;
		}

		int screenWidth = client.getWindow().getScaledWidth();
		int screenHeight = client.getWindow().getScaledHeight();
		int barWidth = config.shieldChargeHudWidth;
		int barHeight = config.shieldChargeHudHeight;
		int yOffset = config.shieldChargeHudYOffset;

		int x = screenWidth / 2 - barWidth / 2;
		int y = screenHeight / 2 + yOffset;

		float progress = (float) clientChargeTicks / config.maxShieldChargeTicks;
		progress = Math.clamp(progress, 0.0F, 1.0F);

		// Background (vanilla XP bar style: solid black)
		context.fill(x, y, x + barWidth, y + barHeight, 0xFF000000);

		// Fill bar (vanilla XP green, gold when full)
		boolean isFull = progress >= 1.0F;
		int fillColor = isFull ? 0xFFFFD700 : 0xFF80FF20;
		int fillWidth = Math.round(barWidth * progress);
		if (fillWidth > 0) {
			context.fill(x, y, x + fillWidth, y + barHeight, fillColor);
		}
	}
}
