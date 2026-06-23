package io.github.ikunkk02.shieldcounter.counter;

public interface ShieldCounterPlayerAccess {
	void shieldCounter$prepare(PendingShieldCounter pendingCounter);

	PendingShieldCounter shieldCounter$consume();
}
