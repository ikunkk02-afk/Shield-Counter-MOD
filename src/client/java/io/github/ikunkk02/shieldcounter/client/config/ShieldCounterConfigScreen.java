package io.github.ikunkk02.shieldcounter.client.config;

import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfig;
import io.github.ikunkk02.shieldcounter.config.ShieldCounterConfigManager;
import me.shedaniel.clothconfig2.api.ConfigBuilder;
import me.shedaniel.clothconfig2.api.ConfigCategory;
import me.shedaniel.clothconfig2.api.ConfigEntryBuilder;
import net.minecraft.client.gui.screen.Screen;
import net.minecraft.text.Text;

public final class ShieldCounterConfigScreen {
	private ShieldCounterConfigScreen() {
	}

	public static Screen create(Screen parent) {
		ShieldCounterConfig editing = ShieldCounterConfigManager.get().validatedCopy();
		ConfigBuilder builder = ConfigBuilder.create()
			.setParentScreen(parent)
			.setTitle(Text.translatable("title.shield-counter.config"));
		ConfigCategory category = builder.getOrCreateCategory(Text.translatable("category.shield-counter.fall_shield"));
		ConfigEntryBuilder entries = builder.entryBuilder();

		category.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.enable_fall_shield_block"),
				editing.enableFallShieldBlock
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_ENABLE_FALL_SHIELD_BLOCK)
			.setTooltip(Text.translatable("option.shield-counter.enable_fall_shield_block.tooltip"))
			.setSaveConsumer(value -> editing.enableFallShieldBlock = value)
			.build());

		category.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.fall_damage_reduction"),
				editing.fallDamageReduction
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_FALL_DAMAGE_REDUCTION)
			.setMin(ShieldCounterConfig.MIN_FALL_DAMAGE_REDUCTION)
			.setMax(ShieldCounterConfig.MAX_FALL_DAMAGE_REDUCTION)
			.setTooltip(Text.translatable("option.shield-counter.fall_damage_reduction.tooltip"))
			.setSaveConsumer(value -> editing.fallDamageReduction = value)
			.build());

		category.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.durability_cost_multiplier"),
				editing.durabilityCostMultiplier
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_DURABILITY_COST_MULTIPLIER)
			.setMin(ShieldCounterConfig.MIN_DURABILITY_COST_MULTIPLIER)
			.setMax(ShieldCounterConfig.MAX_DURABILITY_COST_MULTIPLIER)
			.setTooltip(Text.translatable("option.shield-counter.durability_cost_multiplier.tooltip"))
			.setSaveConsumer(value -> editing.durabilityCostMultiplier = value)
			.build());

		category.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.require_shield_raise_time"),
				editing.requireShieldRaiseTime
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_REQUIRE_SHIELD_RAISE_TIME)
			.setTooltip(Text.translatable("option.shield-counter.require_shield_raise_time.tooltip"))
			.setSaveConsumer(value -> editing.requireShieldRaiseTime = value)
			.build());

		category.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.min_shield_raise_ticks"),
				editing.minShieldRaiseTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_MIN_SHIELD_RAISE_TICKS)
			.setMin(ShieldCounterConfig.MIN_SHIELD_RAISE_TICKS)
			.setMax(ShieldCounterConfig.MAX_SHIELD_RAISE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.min_shield_raise_ticks.tooltip"))
			.setSaveConsumer(value -> editing.minShieldRaiseTicks = value)
			.build());

		builder.setSavingRunnable(() -> ShieldCounterConfigManager.save(editing));
		return builder.build();
	}
}
