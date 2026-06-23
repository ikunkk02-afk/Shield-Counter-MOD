package io.github.ikunkk02.shieldcounter.client.hud;

import io.github.ikunkk02.shieldcounter.charge.ShieldChargeApi;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.fabricmc.fabric.api.client.rendering.v1.HudRenderCallback;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.item.Items;
import net.minecraft.client.render.RenderTickCounter;

public final class ShieldChargeHud {
	private static final int BACKGROUND_COLOR = 0xFF000000;
	private static final int CHARGE_COLOR = 0xFF80FF20;
	private static final int FULL_CHARGE_COLOR = 0xFFFFD700;
	private static final int COOLDOWN_COLOR = 0xFF8A8A8A;

	private ShieldChargeHud() {
	}

	public static void register() {
		HudRenderCallback.EVENT.register(ShieldChargeHud::onHudRender);
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
		boolean holdingShield = client.player.getMainHandStack().isOf(Items.SHIELD)
			|| client.player.getOffHandStack().isOf(Items.SHIELD);

		int screenWidth = client.getWindow().getScaledWidth();
		int screenHeight = client.getWindow().getScaledHeight();
		int barWidth = config.shieldChargeHudWidth;
		int barHeight = config.shieldChargeHudHeight;
		int yOffset = config.shieldChargeHudYOffset;

		int x = screenWidth / 2 - barWidth / 2;
		int y = screenHeight / 2 + yOffset;

		int cooldownTicks = ShieldChargeApi.getShieldChargeCooldownTicks(client.player);
		int cooldownDurationTicks = ShieldChargeApi.getShieldChargeCooldownDurationTicks(client.player);
		boolean onCooldown = cooldownTicks > 0 && cooldownDurationTicks > 0;
		if (!usingShield && !(onCooldown && holdingShield)) {
			return;
		}
		if (!config.enableShieldCharge && !onCooldown) {
			return;
		}

		float progress;
		int fillColor;
		if (onCooldown) {
			progress = 1.0F - (float) cooldownTicks / cooldownDurationTicks;
			fillColor = COOLDOWN_COLOR;
		} else {
			int chargeTicks = ShieldChargeApi.getShieldChargeTicks(client.player);
			progress = (float) chargeTicks / Math.max(1, config.chargeStageThreeTicks);
			boolean isFull = config.getShieldChargeLevel(chargeTicks) >= 3;
			fillColor = isFull ? FULL_CHARGE_COLOR : CHARGE_COLOR;
		}
		progress = Math.clamp(progress, 0.0F, 1.0F);

		context.fill(x, y, x + barWidth, y + barHeight, BACKGROUND_COLOR);
		int fillWidth = Math.round(barWidth * progress);
		if (fillWidth > 0) {
			context.fill(x, y, x + fillWidth, y + barHeight, fillColor);
		}
	}
}
