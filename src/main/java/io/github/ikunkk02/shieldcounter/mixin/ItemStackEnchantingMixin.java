package io.github.ikunkk02.shieldcounter.mixin;

import io.github.ikunkk02.shieldcounter.enchantment.ShieldEnchantingRules;
import net.minecraft.item.ItemStack;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(ItemStack.class)
public abstract class ItemStackEnchantingMixin {
	@Inject(method = "isEnchantable", at = @At("HEAD"), cancellable = true)
	private void shieldCounter$allowShieldEnchantingTable(
		CallbackInfoReturnable<Boolean> cir
	) {
		ItemStack stack = (ItemStack) (Object) this;
		if (ShieldEnchantingRules.canUseEnchantingTable(stack)) {
			cir.setReturnValue(true);
		}
	}
}
