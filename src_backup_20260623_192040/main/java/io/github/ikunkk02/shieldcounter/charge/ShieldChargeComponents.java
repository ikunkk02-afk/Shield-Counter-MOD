package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.ShieldCounter;
import org.ladysnake.cca.api.v3.component.ComponentKey;
import org.ladysnake.cca.api.v3.component.ComponentRegistryV3;
import org.ladysnake.cca.api.v3.entity.EntityComponentFactoryRegistry;
import org.ladysnake.cca.api.v3.entity.EntityComponentInitializer;
import org.ladysnake.cca.api.v3.entity.RespawnCopyStrategy;

public final class ShieldChargeComponents implements EntityComponentInitializer {
	public static final ComponentKey<ShieldChargeComponent> SHIELD_CHARGE =
		ComponentRegistryV3.INSTANCE.getOrCreate(
			ShieldCounter.id("shield_charge"),
			ShieldChargeComponent.class
		);

	@Override
	public void registerEntityComponentFactories(EntityComponentFactoryRegistry registry) {
		registry.registerForPlayers(
			SHIELD_CHARGE,
			PlayerShieldChargeComponent::new,
			RespawnCopyStrategy.NEVER_COPY
		);
	}
}
