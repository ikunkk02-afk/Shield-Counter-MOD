package io.github.ikunkk02.shieldcounter.mixin;

import net.minecraft.entity.LivingEntity;
import net.minecraft.entity.damage.DamageSource;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.registry.tag.DamageTypeTags;
import net.minecraft.util.math.MathHelper;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.ModifyArgs;
import org.spongepowered.asm.mixin.injection.invoke.arg.Args;

@Mixin(PlayerEntity.class)
public abstract class PlayerEntityMixin {
	private static final float SHIELD_FALL_DAMAGE_MULTIPLIER = 0.5F;

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

		if (player.getWorld().isClient()
			|| player.isCreative()
			|| amount <= 0.0F
			|| !source.isIn(DamageTypeTags.IS_FALL)
			|| !player.isUsingItem()) {
			return;
		}

		ItemStack activeItem = player.getActiveItem();
		if (!activeItem.isOf(Items.SHIELD)) {
			return;
		}

		float reducedAmount = amount * SHIELD_FALL_DAMAGE_MULTIPLIER;
		int durabilityDamage = Math.max(1, MathHelper.ceil(amount - reducedAmount));
		activeItem.damage(durabilityDamage, player, LivingEntity.getSlotForHand(player.getActiveHand()));
		args.set(1, reducedAmount);
	}
}
