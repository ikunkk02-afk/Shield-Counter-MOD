# Shield Counter Configuration System Design

## Goal

Add a server-authoritative JSON configuration system for the existing shield fall-damage reduction feature, with a Cloth Config screen exposed through Mod Menu on clients.

This change does not add shield counters, charging, enchantments, Cardinal Components state, or any other gameplay feature.

## Configuration Model

Create a common `ShieldCounterConfig` class with these persisted fields:

| Field | Type | Default | Valid range | Behavior |
| --- | --- | --- | --- | --- |
| `enableFallShieldBlock` | boolean | `true` | n/a | Enables or disables shield-based fall-damage reduction. |
| `fallDamageReduction` | double | `0.5` | `0.0` to `0.95` | Fraction of incoming fall damage prevented. |
| `durabilityCostMultiplier` | double | `1.0` | `0.0` to `5.0` | Multiplies the prevented damage when calculating shield durability loss. |
| `requireShieldRaiseTime` | boolean | `true` | n/a | Requires the active shield to have been raised for the configured duration. |
| `minShieldRaiseTicks` | int | `10` | `0` to `60` | Required active-use ticks when raise-time checking is enabled. |

The file path is `config/shield_counter.json`, resolved through Fabric Loader's config directory.

Values loaded from disk and values submitted by the UI are clamped to their valid ranges before becoming active. Unknown JSON fields are ignored so future versions remain backward compatible.

## Loading, Saving, and Failure Handling

- `ShieldCounter.onInitialize()` loads the common configuration before gameplay can use it.
- If the file is absent, the mod creates the config directory and writes a default configuration.
- If the file is malformed, unreadable, or contains an invalid root value, the mod logs the failure, activates defaults, and rewrites a valid default file instead of crashing.
- Saving replaces the active in-memory configuration only after values have been validated.
- The active configuration is process-local:
  - Integrated single-player uses the local file, and Mod Menu saves take effect immediately.
  - Dedicated servers use their own file and remain authoritative.
  - A multiplayer client cannot modify or override a remote server's configuration.

## Client Configuration UI

Client-only code provides:

- A Cloth Config screen with one category for shield fall-damage settings.
- Boolean controls for the feature and raise-time switches.
- Bounded numeric controls for the reduction ratio, durability multiplier, and minimum raise ticks.
- Tooltips that describe ratios and note that 20 ticks equal one second.
- A save callback that validates, persists, and activates the edited values.
- A Mod Menu API entrypoint returning the Cloth Config screen factory.

Cloth Config and Mod Menu classes remain exclusively under the client source set. Common configuration persistence uses Gson from Minecraft's runtime dependencies and does not reference client classes.

Cloth Config is a required mod dependency because the configured client entrypoint directly uses it. Mod Menu is a suggested dependency because the mod remains functional without the Mod Menu button.

## Gameplay Integration

Keep the existing `PlayerEntity.damage` Mixin and its current server-side checks. Replace hard-coded values with the active configuration:

1. Return without changing damage when `enableFallShieldBlock` is false.
2. Preserve the existing requirements for a positive fall-damage amount, a non-creative player, active item use, and an active vanilla shield.
3. When `requireShieldRaiseTime` is true, require `player.getItemUseTime() >= minShieldRaiseTicks`.
4. Calculate prevented damage as `amount * fallDamageReduction`.
5. Pass `amount - preventedDamage` to the original damage method.
6. If `durabilityCostMultiplier` is zero, do not damage the shield.
7. Otherwise damage the active-hand shield by `max(1, ceil(preventedDamage * durabilityCostMultiplier))`.

A reduction of zero causes no damage reduction and no durability cost. The maximum configured reduction is 95%, so positive incoming damage cannot become fully immune through this feature.

## Dependencies and Metadata

- Add Fabric 1.21.1-compatible Cloth Config and Mod Menu Maven repositories and Gradle dependencies.
- Record their versions in `gradle.properties`.
- Add a `modmenu` entrypoint for the client-only integration class.
- Declare `cloth-config` under `depends` and `modmenu` under `suggests`.
- Do not add or use Cardinal Components API in this change.
- Add English and Simplified Chinese translation keys for the configuration screen labels and tooltips.

## Testing and Verification

Add unit coverage for configuration behavior that can run without launching a client:

- Default values.
- Lower and upper range clamping.
- Valid JSON load.
- Missing-file default generation.
- Malformed JSON fallback and rewrite.
- Durability calculation, including zero multiplier and minimum-one behavior.
- Damage reduction calculation at zero, default, and maximum values.
- Raise-time requirement at below, equal, and above threshold.

Then run:

1. The focused tests and full `gradlew test`.
2. `gradlew build`.
3. A dedicated-server startup smoke test to verify no client-only Cloth Config or Mod Menu class is loaded on the server.

Manual client validation remains necessary for the rendered Mod Menu/Cloth Config screen and immediate integrated-server behavior.
