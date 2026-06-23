package io.github.ikunkk02.shieldcounter.charge;

import io.github.ikunkk02.shieldcounter.ShieldCounter;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.minecraft.item.Items;
import net.minecraft.nbt.NbtCompound;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.registry.RegistryWrapper;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvent;
import net.minecraft.sound.SoundEvents;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.text.Text;

final class PlayerShieldChargeComponent implements ShieldChargeComponent {
	private final PlayerEntity player;
	private final ShieldChargeState state = new ShieldChargeState();

	PlayerShieldChargeComponent(PlayerEntity player) {
		this.player = player;
	}

	@Override
	public int getShieldChargeTicks() {
		return this.state.getChargeTicks();
	}

	@Override
	public void resetShieldCharge() {
		int chargeTicks = this.state.getChargeTicks();
		int cooldownTicks = this.state.getCooldownTicks();
		int cooldownDurationTicks = this.state.getCooldownDurationTicks();
		this.state.reset();
		this.syncIfChanged(chargeTicks, cooldownTicks, cooldownDurationTicks);
	}

	@Override
	public int getShieldChargeCooldownTicks() {
		return this.state.getCooldownTicks();
	}

	@Override
	public int getShieldChargeCooldownDurationTicks() {
		return this.state.getCooldownDurationTicks();
	}

	@Override
	public void setShieldChargeCooldown(int ticks) {
		int chargeTicks = this.state.getChargeTicks();
		int cooldownTicks = this.state.getCooldownTicks();
		int cooldownDurationTicks = this.state.getCooldownDurationTicks();
		this.state.setCooldown(ticks);
		this.syncIfChanged(chargeTicks, cooldownTicks, cooldownDurationTicks);
	}

	@Override
	public void resetShieldChargeCooldown() {
		int chargeTicks = this.state.getChargeTicks();
		int cooldownTicks = this.state.getCooldownTicks();
		int cooldownDurationTicks = this.state.getCooldownDurationTicks();
		this.state.resetCooldown();
		this.syncIfChanged(chargeTicks, cooldownTicks, cooldownDurationTicks);
	}

	@Override
	public boolean isShieldChargeOnCooldown() {
		return this.state.isOnCooldown();
	}

	@Override
	public void resetShieldChargeState() {
		int chargeTicks = this.state.getChargeTicks();
		int cooldownTicks = this.state.getCooldownTicks();
		int cooldownDurationTicks = this.state.getCooldownDurationTicks();
		this.state.resetAll();
		this.syncIfChanged(chargeTicks, cooldownTicks, cooldownDurationTicks);
	}

	@Override
	public void serverTick() {
		ShieldCounterConfig config = ShieldCounterConfigManager.get();
		boolean charging = this.player.isAlive()
			&& this.player.isUsingItem()
			&& this.player.getActiveItem().isOf(Items.SHIELD);
		int chargeTicks = this.state.getChargeTicks();
		int cooldownTicks = this.state.getCooldownTicks();
		int cooldownDurationTicks = this.state.getCooldownDurationTicks();
		int enteredStage = this.state.update(charging, config);
		if (enteredStage > 0 && this.player instanceof ServerPlayerEntity serverPlayer) {
			playStageFeedback(serverPlayer, enteredStage, config);
			ShieldCounter.LOGGER.debug(
				"Player {} reached shield charge stage {}",
				serverPlayer.getName().getString(),
				enteredStage
			);
		}
		this.syncIfChanged(chargeTicks, cooldownTicks, cooldownDurationTicks);
	}

	@Override
	public void readFromNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		this.state.readFromNbt(tag, ShieldCounterConfigManager.get());
	}

	@Override
	public void writeToNbt(NbtCompound tag, RegistryWrapper.WrapperLookup registryLookup) {
		this.state.writeToNbt(tag);
	}

	private void syncIfChanged(int chargeTicks, int cooldownTicks, int cooldownDurationTicks) {
		if (chargeTicks != this.state.getChargeTicks()
			|| cooldownTicks != this.state.getCooldownTicks()
			|| cooldownDurationTicks != this.state.getCooldownDurationTicks()) {
			this.sync();
		}
	}

	private void sync() {
		if (this.player instanceof ServerPlayerEntity) {
			ShieldChargeComponents.SHIELD_CHARGE.sync(this.player);
		}
	}

	private static void playStageFeedback(ServerPlayerEntity player, int stage, ShieldCounterConfig config) {
		ServerWorld world = player.getServerWorld();
		SoundEvent sound;
		float volume;
		float pitch;

		if (stage == 1) {
			sound = SoundEvents.ENTITY_EXPERIENCE_ORB_PICKUP;
			volume = 0.35F;
			pitch = 1.15F;
		} else if (stage == 2) {
			sound = SoundEvents.BLOCK_ENCHANTMENT_TABLE_USE;
			volume = 0.55F;
			pitch = 1.05F;
		} else {
			sound = SoundEvents.ENTITY_PLAYER_LEVELUP;
			volume = 0.75F;
			pitch = 1.1F;
			world.spawnParticles(
				ParticleTypes.END_ROD,
				player.getX(),
				player.getBodyY(0.5D),
				player.getZ(),
				8,
				0.35D,
				0.5D,
				0.35D,
				0.02D
			);
			if (config.showShieldChargeStatusMessage) {
				player.sendMessage(Text.translatable("message.shield-counter.shield_charge_full"), true);
			}
		}

		world.playSound(
			null,
			player.getX(),
			player.getY(),
			player.getZ(),
			sound,
			SoundCategory.PLAYERS,
			volume,
			pitch
		);
	}
}
