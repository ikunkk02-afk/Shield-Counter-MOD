package io.github.ikunkk02.shieldcounter.mixin;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	@ModifyArgs(
		method = "damage",
		at = @At(
			value = "INVOKE",
			target = "Lnet/minecraft/entity/LivingEntity;damage(Lnet/minecraft/entity/damage/DamageSource;F)Z"
		)
	)
	private void shieldCounter$reduceFallDamage(Args args) {
		PlayerEntity player = (PlayerEntity) (Object) this;
		DamageSource source = args.get(0);
		float amount = args.get(1);
		ShieldCounterConfig config = ShieldCounterConfigManager.get();

		if (player.getWorld().isClient()
			|| player.isCreative()
			|| !config.enableFallShieldBlock
			|| amount <= 0.0F
			|| !source.isIn(DamageTypeTags.IS_FALL)
			|| !player.isUsingItem()) {
			return;
		}

		ItemStack activeItem = player.getActiveItem();
		if (!activeItem.isOf(Items.SHIELD)) {
			return;
		}

		if (!config.isRaiseTimeSatisfied(player.getItemUseTime())) {
			return;
		}

		float reducedAmount = config.calculateReducedDamage(amount);
		float preventedDamage = amount - reducedAmount;
		int durabilityDamage = config.calculateDurabilityCost(preventedDamage);
		if (durabilityDamage > 0) {
			activeItem.damage(durabilityDamage, player, LivingEntity.getSlotForHand(player.getActiveHand()));
		}
		args.set(1, reducedAmount);
	}
}
