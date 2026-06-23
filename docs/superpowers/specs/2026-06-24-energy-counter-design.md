# Energy Counter Design

## Goal

Add a second shield-only data-driven enchantment, `shield-counter:energy_counter`, named 蓄能. It stores blocked entity-attack damage and releases the stored damage as `shield_reflect` damage once the next successful block occurs after the configured threshold is reached.

## Behavior

- Only shields with `energy_counter` store or release energy.
- Only successful shield blocks against a `LivingEntity` attacker count.
- `shield_reflect` damage never triggers storage or release, preventing recursive reflection.
- Non-entity damage such as fall, fire, void, drowning, and magic does not trigger storage or release.
- If `shield_counter` and `energy_counter` are both present, the existing shield counter reflection is prepared and applied first; energy counter then either releases extra reflected damage or stores the newly blocked damage.
- Cooldown blocks only energy storage/release. Normal shield blocking, fall shield blocking, and `shield_counter` continue to work.

## Data

Reuse the existing CCA player component and persist:

- `storedShieldDamage` as `float`
- `energyCounterCooldownTicks` as `int`

The public API lives on `ShieldChargeApi` to match the existing component access pattern.

## Config

Add defaults and validation for enable flag, thresholds, multipliers, max stored damage, cooldowns, and actionbar messages. Damage values are finite non-negative doubles/floats; cooldowns are clamped to a bounded tick range.

## Feedback

- Store: actionbar `蓄能：当前值 / 阈值`
- Release level I/II: actionbar `蓄能反击！`, sound, shield durability cost
- Release level III: actionbar `蓄能爆发！`, stronger particles, extra knockback, shield durability cost

## Testing

Unit tests cover config defaults/validation, energy rules, CCA state persistence/cooldown, data-driven resources, and regression behavior for storage/release/cooldown. Full `gradlew build` must pass.
