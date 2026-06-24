package io.github.ikunkk02.shieldcounter.enchantment;

import net.minecraft.component.DataComponentTypes;
import net.minecraft.component.type.ItemEnchantmentsComponent;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;

public final class ShieldEnchantingRules {
	public static final int SHIELD_ENCHANTABILITY = 15;

	private ShieldEnchantingRules() {
	}

	public static boolean canUseEnchantingTable(ItemStack stack) {
		return stack.isOf(Items.SHIELD)
			&& stack.getOrDefault(
				DataComponentTypes.ENCHANTMENTS,
				ItemEnchantmentsComponent.DEFAULT
			).isEmpty();
	}
}
