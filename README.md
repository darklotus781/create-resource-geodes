# 🔷 Create Resource Geodes

**Create Resource Geodes** is a powerful, data-driven addon for Minecraft modpacks that introduces **Catalyst Blocks**—special blocks that can generate valuable resources like ores or blocks over time. It's designed to integrate seamlessly with modded tech, automation, and exploration themes.

This mod was built with **modpack makers in mind**, offering complete customization through **datapacks** and **resource packs**.

---

## 🚀 Features

### 🧱 Catalyst Blocks
- 5 **static catalyst blocks** included by default:
  - `Asurine`, `Crimsite`, `Veridium`, `Ochrum`, and `Generic` catalysts
- Up to **12 fully generic catalysts** (`generic_catalyst_1` → `generic_catalyst_12`)
- Each block can **generate blocks like ores** above it on a configurable cooldown

### 🧠 Datapack-Driven Logic
All catalyst behavior can be controlled with datapacks. Define:
- The **block to generate** (e.g., `minecraft:diamond_ore`)
- The **shape** of the generation area: `SPHERE` or `CUBE`
- The **radius** of that area
- The **cooldown** in ticks (delay between activations)
- The **fill percentage** (density of block placement)

### 🔨 Catalyst Activator Wand
Hidden away in Abandoned Mineshaft Loot Chests and in the Wandering Traders backpack, you'll find a Catalyst Activator Wand.  
This tool is used to move the Catalyst in the direction you click the wand on the block and the distance is configured in the configs.  
Be careful though, the wand does have durability and cannot be crafted without a custom recipe which you can add using KubeJS.  

The wand is also used in default recipes, which also consume 1 durability point.

### 💎 Catalyst Agitator  
An item used to **activate** catalysts and trigger their generation.
Supports both cube and sphere-based placement logic, animated over time.

### 📦 Resource Pack Support
Customize:
- Catalyst block textures
- Catalyst item models
- Lang entries for display names

---

## 📁 Datapack Setup

Place datapacks in:
```
saves/<your-world>/datapacks/
```

Each catalyst must be defined like:
```
data/createresourcegeodes/createresourcegeodes/catalysts/generic_catalyst_1.json
```

### Example JSON:
```json
{
  "generator": "minecraft:diamond_ore",
  "cooldown": 100,
  "shape": "SPHERE",
  "radius": 3,
  "fill_percentage": 0.75
}
```

---

## 🎨 Resource Pack Setup

To change the name, model, or texture of a generic catalyst:
```
assets/createresourcegeodes/
├── lang/en_us.json
├── models/block/generic_catalyst_1.json
├── models/item/generic_catalyst_1.json
├── blockstates/generic_catalyst_1.json
└── textures/block/my_custom_texture.png
```

---

## 🧪 Recipe Integration

You can define recipes for any catalyst or the catalyst agitator using standard Minecraft recipe JSON:

**`data/createresourcegeodes/recipes/generic_catalyst_1.json`**
```json
{
  "type": "minecraft:crafting_shaped",
  "category": "misc",
  "key": {
    "B": {
      "item": "minecraft:infested_stone"
    },
    "W": {
      "item": "createresourcegeodes:catalyst_core"
    }
  },
  "pattern": [
    "BBB",
    "BWB",
    "BBB"
  ],
  "result": {
    "count": 1,
    "id": "createresourcegeodes:generic_catalyst_1"
  }
}
```

---

## 🛠 Configuration

The mod includes a config file for global performance tuning:
- `replaceAe2Meteor` — Should we replace the mystery box inside AE2 Meteors with the Catalyst?
- `moveCatalystDistance` — How many blocks should a Catalyst Block be moved when right-clicked with the Activator Wand?
- `catalystMoveIgnoreWater` — Can a Catalyst move into a Water Source or Flowing Water block?
- `catalystBlocksPerTick` — max number of blocks placed per tick (defaults to 5) supports 1-100

---

## 👨‍🔧 For Modpack Makers

You can:
- Override or add **new catalysts**
- Change visuals and names
- Customize generation logic per catalyst
- Fully replace or remove recipes
- Define cooldowns, generation logic, shapes, and more — **no Java required**

---

## 🧰 Dependencies

- Minecraft 1.21.1
- [NeoForge](https://neoforged.net) (latest version)
- Create

Optional support for:
- [KubeJS](https://www.curseforge.com/minecraft/mc-mods/kubejs) (for advanced scripting)
- Applied Energistics 2 (AE2)
---

## 📝 License

MIT License. Use freely in modpacks.

---

## 💬 Support & Contributions  

Open an issue or pull request if you encounter bugs or want to contribute enhancements. Suggestions welcome!  

---  
When reporting an issue put the version number before the issue title! Such as [FULL][0.0.1] My game is broken! Also include any added mods you may have put in, into the description of the issue.

|                                                         You can also find us on Discord for help<br>or just to chat!                                                          |
|:-----------------------------------------------------------------------------------------------------------------------------------------------------------------------------:|
| <a href="https://discord.gg/XH7zCjgUHb"><img src="https://discordapp.com/assets/fc0b01fe10a0b8c602fb0106d8189d9b.png" alt="Join us on Discord!"  width="200" height="68"></a> |

