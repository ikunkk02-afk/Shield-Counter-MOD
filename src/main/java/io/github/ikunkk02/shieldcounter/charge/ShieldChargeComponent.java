package io.github.ikunkk02.shieldcounter.charge;

import org.ladysnake.cca.api.v3.component.ComponentV3;
import org.ladysnake.cca.api.v3.component.sync.AutoSyncedComponent;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public interface ShieldChargeComponent extends ComponentV3, AutoSyncedComponent, ServerTickingComponent {
	int getShieldChargeTicks();

	void resetShieldCharge();

	int getShieldChargeCooldownTicks();

	int getShieldChargeCooldownDurationTicks();

	void setShieldChargeCooldown(int ticks);

	void resetShieldChargeCooldown();

	boolean isShieldChargeOnCooldown();

	void resetShieldChargeState();
}
