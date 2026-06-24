package io.github.ikunkk02.shieldcounter.mixin;

import io.github.ikunkk02.shieldcounter.enchantment.ShieldEnchantingRules;
import net.minecraft.item.Item;
import net.minecraft.item.Items;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(Item.class)
public abstract class ItemEnchantabilityMixin {
	@Inject(method = "getEnchantability", at = @At("HEAD"), cancellable = true)
	private void shieldCounter$getShieldEnchantability(
		CallbackInfoReturnable<Integer> cir
	) {
		if ((Object) this == Items.SHIELD) {
			cir.setReturnValue(ShieldEnchantingRules.SHIELD_ENCHANTABILITY);
		}
	}
}
