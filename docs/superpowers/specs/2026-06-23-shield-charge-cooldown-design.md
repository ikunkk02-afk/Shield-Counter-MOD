# Shield Charge Cooldown Design

## Scope

Strengthen the existing Fabric 1.21.1 shield-charge system without changing the
fall-damage blocking path or adding enchantments.

## State and authority

The existing Cardinal Components player component remains authoritative. It
stores:

- current shield charge ticks;
- remaining shield charge cooldown ticks;
- the original cooldown duration used by the HUD.

The component is synchronized to the owning client. Releasing the shield clears
charge without starting cooldown. A full-charge counter clears charge and starts
the configured cooldown for Shield Counter I, II, or III. During cooldown,
charging is disabled, but blocking and countering remain available.

## Counter behavior

Shield counter preparation reads the synchronized server-side charge component.
If cooldown is active, the effective charge level is forced to zero. Otherwise,
the existing stage is used:

- Shield Counter I: 25%, 35%, 45%, 60%;
- Shield Counter II: 50%, 65%, 80%, 90%;
- Shield Counter III: 100% at every stage, with stage-scaled knockback.

Only a stage-three counter starts charge cooldown. Partial charge continues to
respect the existing `consumeChargeOnCounter` option but does not start cooldown.
A stage-three counter always consumes its charge as required.

## Feedback and HUD

The transition into charge stage three triggers one level-up sound, one small
particle burst around the player, and, when enabled, the action-bar message
`盾牌已满蓄力`. The transition state prevents per-tick repetition.

The HUD is visible only while actively using a shield:

- available charge: green progress;
- full charge: gold full bar;
- cooldown: gray recovery progress from empty to full.

The HUD reads the synchronized player component instead of maintaining a second
client-only charge counter.

## Configuration

Add and validate:

- `enableShieldChargeCooldown = true`;
- `shieldChargeCooldownLevel1 = 40`;
- `shieldChargeCooldownLevel2 = 50`;
- `shieldChargeCooldownLevel3 = 60`;
- `showShieldChargeStatusMessage = true`.

Cooldown values are clamped to 0 through 200 and exposed in Cloth Config.

## Verification

Unit tests cover state transitions, NBT round trips, cooldown ranges, effective
counter stages, configured ratios, and cooldown duration selection. Final
verification runs `gradlew test` and `gradlew build`.
