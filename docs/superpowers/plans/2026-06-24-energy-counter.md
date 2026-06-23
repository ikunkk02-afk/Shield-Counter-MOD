# Energy Counter Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add the shield-only `energy_counter` enchantment with CCA-backed stored damage, cooldown, reflection, feedback, and tests.

**Architecture:** Use data-driven enchantment JSON for the new enchantment. Reuse the existing player CCA component to store energy counter state and expose it through `ShieldChargeApi`. Extend `ShieldBlockMixin` after the existing `shield_counter` preparation so shield counter remains first and energy counter is additive.

**Tech Stack:** Fabric 1.21.1, Yarn mappings, Cardinal Components API, data-driven enchantments, JUnit, Gradle.

---

### Task 1: Resource and config tests

**Files:**
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/counter/ShieldCounterResourcesTest.java`
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/config/ShieldCounterConfigTest.java`
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/config/ShieldCounterConfigManagerTest.java`

- [ ] Write failing tests for `energy_counter.json`, non-treasure tag inclusion, default config values, validation, and legacy config rewrite.
- [ ] Run focused tests and confirm failures mention missing fields/resources.

### Task 2: Energy state and rules tests

**Files:**
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeStateTest.java`
- Create: `src/test/java/io/github/ikunkk02/shieldcounter/counter/EnergyCounterRulesTest.java`

- [ ] Write failing tests for stored damage accumulation, max clamp, cooldown ticking, NBT round trip, release threshold, level tuning, cooldown blocking, and reflected-damage suppression.
- [ ] Run focused tests and confirm failures are caused by missing implementation.

### Task 3: Implement config, state, API, and rules

**Files:**
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/config/ShieldCounterConfig.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeState.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeComponent.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/PlayerShieldChargeComponent.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeApi.java`
- Create: `src/main/java/io/github/ikunkk02/shieldcounter/counter/EnergyCounterRules.java`

- [ ] Add config fields, validation, and helper getters.
- [ ] Add stored damage and cooldown fields to the CCA state with NBT persistence.
- [ ] Add public API methods requested by the user.
- [ ] Implement pure rules for store/release calculations.
- [ ] Run focused tests until green.

### Task 4: Data-driven enchantment and runtime Mixin integration

**Files:**
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/enchantment/ModEnchantments.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/mixin/ShieldBlockMixin.java`
- Create: `src/main/resources/data/shield-counter/enchantment/energy_counter.json`
- Modify: `src/main/resources/data/minecraft/tags/enchantment/non_treasure.json`

- [ ] Add `ENERGY_COUNTER` registry key only; do not Java-register the enchantment.
- [ ] Add shield-only enchantment JSON and tag inclusion.
- [ ] In `ShieldBlockMixin`, after shield counter preparation, process energy counter only for successful entity shield blocks and never for `shield_reflect`.
- [ ] Use existing `shield_reflect` damage source for energy release.
- [ ] Apply release durability cost, sound, particles, and level III knockback.

### Task 5: UX and verification

**Files:**
- Modify: `src/client/java/io/github/ikunkk02/shieldcounter/client/config/ShieldCounterConfigScreen.java`
- Modify: `src/client/resources/assets/shield-counter/lang/en_us.json`
- Modify: `src/client/resources/assets/shield-counter/lang/zh_cn.json`

- [ ] Add config screen entries and translations.
- [ ] Validate JSON files.
- [ ] Run `.\gradlew.bat test`.
- [ ] Run `.\gradlew.bat build`.
- [ ] Confirm `PlayerEntityMixin` has no diff.
