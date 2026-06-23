# Shield Charge Cooldown Implementation Plan

> **For agentic workers:** REQUIRED SUB-SKILL: Use superpowers:subagent-driven-development (recommended) or superpowers:executing-plans to implement this plan task-by-task. Steps use checkbox (`- [ ]`) syntax for tracking.

**Goal:** Add authoritative shield-charge cooldown, full-charge feedback, and synchronized charge/cooldown HUD behavior.

**Architecture:** Extend the existing CCA player component with cooldown state and synchronization. Keep gameplay decisions server-side, expose the state through `ShieldChargeApi`, and make the client HUD a read-only renderer of synchronized state.

**Tech Stack:** Java 21, Fabric 1.21.1, Cardinal Components API 6.1.3, Cloth Config, JUnit 5, Gradle.

---

### Task 1: Specify cooldown state and configuration

**Files:**
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeStateTest.java`
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/config/ShieldCounterConfigTest.java`
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/config/ShieldCounterConfigManagerTest.java`

- [ ] **Step 1: Write failing tests**

Test that cooldown blocks accumulation, counts down, can be set/reset, survives
NBT round trips, defaults to 40/50/60 ticks, and clamps values to 0 through 200.

- [ ] **Step 2: Verify the tests fail**

```powershell
.\gradlew.bat test --tests "*ShieldChargeStateTest" --tests "*ShieldCounterConfigTest" --tests "*ShieldCounterConfigManagerTest"
```

Expected: compilation or assertion failures because cooldown fields and methods
do not exist.

- [ ] **Step 3: Implement minimal state and configuration**

Add `shieldChargeCooldownTicks` and its initial duration to
`ShieldChargeState`; add validated configuration fields and a level-to-duration
method to `ShieldCounterConfig`.

- [ ] **Step 4: Verify the focused tests pass**

```powershell
.\gradlew.bat test --tests "*ShieldChargeStateTest" --tests "*ShieldCounterConfigTest" --tests "*ShieldCounterConfigManagerTest"
```

Expected: all focused tests pass.

### Task 2: Specify effective counter behavior

**Files:**
- Modify: `src/test/java/io/github/ikunkk02/shieldcounter/counter/ShieldCounterRulesTest.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/counter/ShieldCounterRules.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/counter/PendingShieldCounter.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/mixin/ShieldBlockMixin.java`

- [ ] **Step 1: Write failing tests**

Test that cooldown forces effective charge level zero and that full-charge
counters select the configured 40/50/60 cooldown while partial charge selects
zero.

- [ ] **Step 2: Verify the tests fail**

```powershell
.\gradlew.bat test --tests "*ShieldCounterRulesTest"
```

Expected: failures because cooldown rule methods do not exist.

- [ ] **Step 3: Implement minimal rule and Mixin integration**

Capture the effective charge level and selected cooldown in
`PendingShieldCounter`; remove the UUID cooldown map; apply charge reset and CCA
cooldown only after the counter completes.

- [ ] **Step 4: Verify counter tests pass**

```powershell
.\gradlew.bat test --tests "*ShieldCounterRulesTest"
```

Expected: all counter rule tests pass.

### Task 3: Synchronize component state and render it

**Files:**
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeComponent.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/PlayerShieldChargeComponent.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeApi.java`
- Modify: `src/main/java/io/github/ikunkk02/shieldcounter/charge/ShieldChargeEvents.java`
- Modify: `src/client/java/io/github/ikunkk02/shieldcounter/client/hud/ShieldChargeHud.java`

- [ ] **Step 1: Implement the tested component boundary**

Expose the required public cooldown methods, synchronize changed component
state, reset transient state on death/disconnect, and render green/gold/gray
bars from CCA state.

- [ ] **Step 2: Compile both source sets**

```powershell
.\gradlew.bat classes testClasses
```

Expected: Java compilation succeeds for main, client, and test sources.

### Task 4: Add configuration UI and localized feedback

**Files:**
- Modify: `src/client/java/io/github/ikunkk02/shieldcounter/client/config/ShieldCounterConfigScreen.java`
- Modify: `src/client/resources/assets/shield-counter/lang/en_us.json`
- Modify: `src/client/resources/assets/shield-counter/lang/zh_cn.json`

- [ ] **Step 1: Add Cloth Config entries and translations**

Expose all five new settings and add the full-charge action-bar translation.

- [ ] **Step 2: Run resource and unit verification**

```powershell
.\gradlew.bat test
```

Expected: all tests pass.

### Task 5: Final verification

**Files:**
- Inspect all modified files.

- [ ] **Step 1: Run the full build**

```powershell
.\gradlew.bat build
```

Expected: `BUILD SUCCESSFUL`.

- [ ] **Step 2: Review scope**

Confirm that `PlayerEntityMixin#shieldCounter$reduceFallDamage` is unchanged,
no enchantment was added, and backup directories remain untouched.
