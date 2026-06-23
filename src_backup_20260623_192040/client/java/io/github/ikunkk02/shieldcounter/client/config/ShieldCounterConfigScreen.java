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
		ConfigCategory fallCategory = builder.getOrCreateCategory(
			Text.translatable("category.shield-counter.fall_shield")
		);
		ConfigCategory chargeCategory = builder.getOrCreateCategory(
			Text.translatable("category.shield-counter.shield_charge")
		);
		ConfigEntryBuilder entries = builder.entryBuilder();

		fallCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.enable_fall_shield_block"),
				editing.enableFallShieldBlock
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_ENABLE_FALL_SHIELD_BLOCK)
			.setTooltip(Text.translatable("option.shield-counter.enable_fall_shield_block.tooltip"))
			.setSaveConsumer(value -> editing.enableFallShieldBlock = value)
			.build());

		fallCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.fall_damage_reduction"),
				editing.fallDamageReduction
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_FALL_DAMAGE_REDUCTION)
			.setMin(ShieldCounterConfig.MIN_FALL_DAMAGE_REDUCTION)
			.setMax(ShieldCounterConfig.MAX_FALL_DAMAGE_REDUCTION)
			.setTooltip(Text.translatable("option.shield-counter.fall_damage_reduction.tooltip"))
			.setSaveConsumer(value -> editing.fallDamageReduction = value)
			.build());

		fallCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.durability_cost_multiplier"),
				editing.durabilityCostMultiplier
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_DURABILITY_COST_MULTIPLIER)
			.setMin(ShieldCounterConfig.MIN_DURABILITY_COST_MULTIPLIER)
			.setMax(ShieldCounterConfig.MAX_DURABILITY_COST_MULTIPLIER)
			.setTooltip(Text.translatable("option.shield-counter.durability_cost_multiplier.tooltip"))
			.setSaveConsumer(value -> editing.durabilityCostMultiplier = value)
			.build());

		fallCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.require_shield_raise_time"),
				editing.requireShieldRaiseTime
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_REQUIRE_SHIELD_RAISE_TIME)
			.setTooltip(Text.translatable("option.shield-counter.require_shield_raise_time.tooltip"))
			.setSaveConsumer(value -> editing.requireShieldRaiseTime = value)
			.build());

		fallCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.min_shield_raise_ticks"),
				editing.minShieldRaiseTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_MIN_SHIELD_RAISE_TICKS)
			.setMin(ShieldCounterConfig.MIN_SHIELD_RAISE_TICKS)
			.setMax(ShieldCounterConfig.MAX_SHIELD_RAISE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.min_shield_raise_ticks.tooltip"))
			.setSaveConsumer(value -> editing.minShieldRaiseTicks = value)
			.build());

		chargeCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.enable_shield_charge"),
				editing.enableShieldCharge
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_ENABLE_SHIELD_CHARGE)
			.setTooltip(Text.translatable("option.shield-counter.enable_shield_charge.tooltip"))
			.setSaveConsumer(value -> editing.enableShieldCharge = value)
			.build());

		chargeCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.max_shield_charge_ticks"),
				editing.maxShieldChargeTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_MAX_SHIELD_CHARGE_TICKS)
			.setMin(ShieldCounterConfig.MIN_MAX_SHIELD_CHARGE_TICKS)
			.setMax(ShieldCounterConfig.MAX_MAX_SHIELD_CHARGE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.max_shield_charge_ticks.tooltip"))
			.setSaveConsumer(value -> editing.maxShieldChargeTicks = value)
			.build());

		chargeCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.charge_stage_one_ticks"),
				editing.chargeStageOneTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_CHARGE_STAGE_ONE_TICKS)
			.setMin(ShieldCounterConfig.MIN_CHARGE_STAGE_TICKS)
			.setMax(ShieldCounterConfig.MAX_MAX_SHIELD_CHARGE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.charge_stage_one_ticks.tooltip"))
			.setSaveConsumer(value -> editing.chargeStageOneTicks = value)
			.build());

		chargeCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.charge_stage_two_ticks"),
				editing.chargeStageTwoTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_CHARGE_STAGE_TWO_TICKS)
			.setMin(ShieldCounterConfig.MIN_CHARGE_STAGE_TICKS)
			.setMax(ShieldCounterConfig.MAX_MAX_SHIELD_CHARGE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.charge_stage_two_ticks.tooltip"))
			.setSaveConsumer(value -> editing.chargeStageTwoTicks = value)
			.build());

		chargeCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.charge_stage_three_ticks"),
				editing.chargeStageThreeTicks
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_CHARGE_STAGE_THREE_TICKS)
			.setMin(ShieldCounterConfig.MIN_CHARGE_STAGE_TICKS)
			.setMax(ShieldCounterConfig.MAX_MAX_SHIELD_CHARGE_TICKS)
			.setTooltip(Text.translatable("option.shield-counter.charge_stage_three_ticks.tooltip"))
			.setSaveConsumer(value -> editing.chargeStageThreeTicks = value)
			.build());

		ConfigCategory hudCategory = builder.getOrCreateCategory(
			Text.translatable("category.shield-counter.shield_charge_hud")
		);

		hudCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.enable_shield_charge_hud"),
				editing.enableShieldChargeHud
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_ENABLE_SHIELD_CHARGE_HUD)
			.setTooltip(Text.translatable("option.shield-counter.enable_shield_charge_hud.tooltip"))
			.setSaveConsumer(value -> editing.enableShieldChargeHud = value)
			.build());

		hudCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.shield_charge_hud_y_offset"),
				editing.shieldChargeHudYOffset
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_SHIELD_CHARGE_HUD_Y_OFFSET)
			.setMin(ShieldCounterConfig.MIN_SHIELD_CHARGE_HUD_Y_OFFSET)
			.setMax(ShieldCounterConfig.MAX_SHIELD_CHARGE_HUD_Y_OFFSET)
			.setTooltip(Text.translatable("option.shield-counter.shield_charge_hud_y_offset.tooltip"))
			.setSaveConsumer(value -> editing.shieldChargeHudYOffset = value)
			.build());

		hudCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.shield_charge_hud_width"),
				editing.shieldChargeHudWidth
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_SHIELD_CHARGE_HUD_WIDTH)
			.setMin(ShieldCounterConfig.MIN_SHIELD_CHARGE_HUD_WIDTH)
			.setMax(ShieldCounterConfig.MAX_SHIELD_CHARGE_HUD_WIDTH)
			.setTooltip(Text.translatable("option.shield-counter.shield_charge_hud_width.tooltip"))
			.setSaveConsumer(value -> editing.shieldChargeHudWidth = value)
			.build());

		hudCategory.addEntry(entries.startIntField(
				Text.translatable("option.shield-counter.shield_charge_hud_height"),
				editing.shieldChargeHudHeight
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_SHIELD_CHARGE_HUD_HEIGHT)
			.setMin(ShieldCounterConfig.MIN_SHIELD_CHARGE_HUD_HEIGHT)
			.setMax(ShieldCounterConfig.MAX_SHIELD_CHARGE_HUD_HEIGHT)
			.setTooltip(Text.translatable("option.shield-counter.shield_charge_hud_height.tooltip"))
			.setSaveConsumer(value -> editing.shieldChargeHudHeight = value)
			.build());

		ConfigCategory counterCategory = builder.getOrCreateCategory(
			Text.translatable("category.shield-counter.shield_counter")
		);

		counterCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.enable_shield_counter"),
				editing.enableShieldCounter
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_ENABLE_SHIELD_COUNTER)
			.setTooltip(Text.translatable("option.shield-counter.enable_shield_counter.tooltip"))
			.setSaveConsumer(value -> editing.enableShieldCounter = value)
			.build());

		counterCategory.addEntry(entries.startBooleanToggle(
				Text.translatable("option.shield-counter.consume_charge_on_counter"),
				editing.consumeChargeOnCounter
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_CONSUME_CHARGE_ON_COUNTER)
			.setTooltip(Text.translatable("option.shield-counter.consume_charge_on_counter.tooltip"))
			.setSaveConsumer(value -> editing.consumeChargeOnCounter = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_durability_cost_multiplier"),
				editing.counterDurabilityCostMultiplier
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_DURABILITY_COST_MULTIPLIER)
			.setMin(ShieldCounterConfig.MIN_COUNTER_DURABILITY_COST_MULTIPLIER)
			.setMax(ShieldCounterConfig.MAX_COUNTER_DURABILITY_COST_MULTIPLIER)
			.setTooltip(Text.translatable(
				"option.shield-counter.counter_durability_cost_multiplier.tooltip"
			))
			.setSaveConsumer(value -> editing.counterDurabilityCostMultiplier = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_level1_base_ratio"),
				editing.counterLevel1BaseRatio
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_LEVEL1_BASE_RATIO)
			.setMin(ShieldCounterConfig.MIN_COUNTER_RATIO)
			.setMax(ShieldCounterConfig.MAX_COUNTER_RATIO)
			.setTooltip(Text.translatable("option.shield-counter.counter_level1_base_ratio.tooltip"))
			.setSaveConsumer(value -> editing.counterLevel1BaseRatio = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_level2_base_ratio"),
				editing.counterLevel2BaseRatio
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_LEVEL2_BASE_RATIO)
			.setMin(ShieldCounterConfig.MIN_COUNTER_RATIO)
			.setMax(ShieldCounterConfig.MAX_COUNTER_RATIO)
			.setTooltip(Text.translatable("option.shield-counter.counter_level2_base_ratio.tooltip"))
			.setSaveConsumer(value -> editing.counterLevel2BaseRatio = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_level3_base_ratio"),
				editing.counterLevel3BaseRatio
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_LEVEL3_BASE_RATIO)
			.setMin(ShieldCounterConfig.MIN_COUNTER_RATIO)
			.setMax(ShieldCounterConfig.MAX_COUNTER_RATIO)
			.setTooltip(Text.translatable("option.shield-counter.counter_level3_base_ratio.tooltip"))
			.setSaveConsumer(value -> editing.counterLevel3BaseRatio = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_knockback_level3_base"),
				editing.counterKnockbackLevel3Base
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_KNOCKBACK_LEVEL3_BASE)
			.setMin(ShieldCounterConfig.MIN_COUNTER_KNOCKBACK)
			.setMax(ShieldCounterConfig.MAX_COUNTER_KNOCKBACK)
			.setTooltip(Text.translatable(
				"option.shield-counter.counter_knockback_level3_base.tooltip"
			))
			.setSaveConsumer(value -> editing.counterKnockbackLevel3Base = value)
			.build());

		counterCategory.addEntry(entries.startDoubleField(
				Text.translatable("option.shield-counter.counter_knockback_level3_full_charge"),
				editing.counterKnockbackLevel3FullCharge
			)
			.setDefaultValue(ShieldCounterConfig.DEFAULT_COUNTER_KNOCKBACK_LEVEL3_FULL_CHARGE)
			.setMin(ShieldCounterConfig.MIN_COUNTER_KNOCKBACK)
			.setMax(ShieldCounterConfig.MAX_COUNTER_KNOCKBACK)
			.setTooltip(Text.translatable(
				"option.shield-counter.counter_knockback_level3_full_charge.tooltip"
			))
			.setSaveConsumer(value -> editing.counterKnockbackLevel3FullCharge = value)
			.build());

		builder.setSavingRunnable(() -> ShieldCounterConfigManager.save(editing));
		return builder.build();
	}
}
