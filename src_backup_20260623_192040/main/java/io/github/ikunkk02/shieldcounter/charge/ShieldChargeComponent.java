package io.github.ikunkk02.shieldcounter.charge;

import org.ladysnake.cca.api.v3.component.ComponentV3;
import org.ladysnake.cca.api.v3.component.tick.ServerTickingComponent;

public interface ShieldChargeComponent extends ComponentV3, ServerTickingComponent {
	int getShieldChargeTicks();

	void resetShieldCharge();
}
