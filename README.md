# Shield Counter

Shield Counter is a Fabric mod for Minecraft 1.21.1 that expands vanilla shield combat with fall-damage blocking, shield charging, counterattacks, energy storage, HUD feedback, configuration, and enchanting-table support for shields.

## Features

- Reduces fall damage while the player is actively using a shield.
- Adds the Shield Counter enchantment for reflected shield counterattacks.
- Adds the Energy Counter enchantment for storing blocked damage and releasing it back at attackers.
- Adds shield charge stages, cooldown feedback, status messages, and a compact HUD bar.
- Lets vanilla shields receive compatible shield enchantments from the enchanting table.
- Keeps gameplay logic server-side and uses the client for HUD, sounds, translations, and configuration UI.
- Includes separate trigger sounds for Shield Counter and Energy Counter.

## Enchantments

### Shield Counter

Shield Counter is a shield-only enchantment with three levels. It improves counterattack behavior when blocking with a shield and can consume stored shield charge depending on configuration.

### Energy Counter

Energy Counter is a shield-only enchantment with three levels. It stores qualifying blocked damage, then releases the stored energy when the configured threshold and cooldown rules are satisfied.

### Enchanting Table Support

With this mod installed, vanilla shields can be placed in the enchanting table. The table can offer this mod's shield enchantments and vanilla enchanting-table enchantments that already support durable items, such as Unbreaking. Treasure enchantments such as Mending still follow vanilla rules and are not forced into the table pool.

## Configuration

Configuration is available through Mod Menu when installed, and is backed by Cloth Config. The config includes:

- Fall-damage shield blocking toggle, reduction, durability cost, and required shield raise time.
- Shield charge stage timing and per-level cooldowns.
- Shield charge HUD position and size.
- Shield Counter damage ratios, knockback, durability cost, and charge consumption.
- Energy Counter thresholds, damage multipliers, stored-damage caps, cooldowns, and messages.

## Requirements

- Minecraft 1.21.1
- Fabric Loader 0.19.3 or newer
- Fabric API
- Cloth Config
- Cardinal Components API base and entity modules
- Java 21 or newer
- Mod Menu is optional but recommended for editing config in game.

## Build

```shell
./gradlew build
```

The built mod jar is generated under `build/libs/`.

## Verification

The current release was checked with:

```shell
./gradlew test
./gradlew build --rerun-tasks
./gradlew runClient
```

## License

This project is available under the MIT License.
