# Resource Geodes & Catalysts

[![Modrinth](https://badges.moddingx.org/modrinth/downloads/ayR06Fno)](https://modrinth.com/mod/resource-geodes-catalysts)
[![CurseForge](https://badges.moddingx.org/curseforge/downloads/952886)](https://www.curseforge.com/minecraft/mc-mods/create-resource-geodes)

**Resource Geodes & Catalysts** introduces a fully data-driven **renewable resource generation system** for modpack creators and players who love automation, exploration, and configurability.

---

## Overview

At its core are **Catalysts** — powerful blocks inspired by the Create mod’s Ore Stone variants. These are found **embedded in geode-like structures underground** and, when **activated**, they generate ores or modded resources over time.

Now featuring:

* **Tiered Agitators** (1–4) for progressive activation
* **Custom item agitator support** (any vanilla/modded item)
* **Full datapack-driven configuration** — no Java required!
* **Integration with JEI & Jade** for an intuitive player experience

---

## Catalyst Types

* **Asurine**, **Crimsite**, **Ochrum**, and **Veridium** catalysts — naturally generated, ready to use.
* **Generic Catalysts (1–12)** — completely data-driven:

    * Define generated blocks or items
    * Customize cooldowns, density, and shape (cube/sphere)
    * Require tiered or custom agitators for activation
 
**Learn More:** [Resource Geodes Wiki →](https://github.com/darklotus781/create-resource-geodes/wiki)

---

## Activation System

Catalysts activate in two different ways:

### **Tier-Based Agitators**

Use a **Catalyst Agitator** item with a tier equal to or greater than the catalyst’s requirement.

### **Custom Item Agitators**

Define *any item* — vanilla or modded — as the agitator via datapack JSON:

```json
{
  "required_item": "minecraft:gunpowder"
}
```

> Only one system (tiered or custom) can be used per catalyst.

Activated catalysts generate ores in **configurable shapes** and **cooldowns**, complete with particle and sound feedback.

---

## Crafting & Tools

* **Catalyst Core** — Used to craft catalysts if enabled. Default recipes are provided but can be overridden.
* **Catalyst Agitator (Tier 1–4)** — Used to activate catalysts. Only Tier 1 is craftable by default.
* **Catalyst Activator Wand** — Moves catalysts to new valid positions or reclaims them if placed by a player.

    * Found in **Mineshaft chests**, dropped by the **Warden**, and rarely sold by the **Wandering Trader**.

---

## Modpack Integration

Resource Geodes was built **for modpack makers**:

* 100% **data-driven** JSON configuration
* **JEI & Jade support** out of the box
* Define **new catalysts, agitators, cooldowns, shapes, and sounds**
* **Custom textures and models** supported via resource packs

All behavior — from activation logic to world generation — can be tuned with data packs.

---

## Showcase Video

[![IMAGE ALT TEXT HERE](https://img.youtube.com/vi/iaqc0Oab4-4/0.jpg)](https://youtu.be/iaqc0Oab4-4)

---

## Installation

1. Install **NeoForge** for Minecraft **1.21.1**.
2. Drop the mod JAR into your `mods/` folder.
3. Launch the game once to generate configs and datapack folders.

---

## Compatibility

* **Minecraft:** 1.21.1
* **Loader:** NeoForge
* **Required Modes:** Create (base geodes / catalysts are still hard coded)
* **Optional Mods:** Jade, JEI, KubeJS, AE2
* **Datapacks:** Fully supported for all catalyst behavior

---

## License

This project is licensed under the **GPLv3 License**. You are free to use, modify, and distribute it in modpacks.
If you use any of the code in a new mod, the mod must also be GPLv3 and credit this mod.

---

## Credits

* **DarkLotus / LithiumCraft** — Development, concepts, art, and documentation
* **Create Mod Team** — Inspiration for Catalyst and Ore Stone design
* **Community & Testers** — Balancing, QA, and feature feedback

---

## Links

* [GitHub Repository](https://github.com/darklotus781/create-resource-geodes)
* [Modrinth Page](https://modrinth.com/mod/resource-geodes-catalysts)
* [CurseForge Page](https://www.curseforge.com/minecraft/mc-mods/create-resource-geodes)
* [Discord Community](https://discord.gg/XH7zCjgUHb)

---

### Need Help?

Questions, suggestions, or want to share your creations?
Join the community on [Discord](https://discord.gg/XH7zCjgUHb) — we’d love to see what you’re building!
