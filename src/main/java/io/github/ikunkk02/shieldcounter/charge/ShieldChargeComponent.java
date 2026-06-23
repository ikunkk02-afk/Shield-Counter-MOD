package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import org.ladysnake.cca.api.v3.component.ComponentV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public interface ShieldChargeComponent extends ComponentV3, AutoSyncedComponent, ServerTickingComponent {
	int getShieldChargeTicks();

	void resetShieldCharge();

	int consumeShieldChargeForCounter(int enchantmentLevel, ShieldCounterConfig config);

	int getShieldChargeCooldownTicks();

	int getShieldChargeCooldownDurationTicks();

	void setShieldChargeCooldown(int ticks);

	void resetShieldChargeCooldown();

	boolean isShieldChargeOnCooldown();

	float getStoredShieldDamage();

	void addStoredShieldDamage(float amount, float maxStoredDamage);

	void resetStoredShieldDamage();

	int getEnergyCounterCooldownTicks();

	void setEnergyCounterCooldown(int ticks);

	boolean isEnergyCounterOnCooldown();

	void resetShieldChargeState();
}
