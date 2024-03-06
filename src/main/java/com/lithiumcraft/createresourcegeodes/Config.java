package com.lithiumcraft.createresourcegeodes;

import net.minecraft.resources.ResourceLocation;
import net.minecraftforge.common.ForgeConfigSpec;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.fml.event.config.ModConfigEvent;
import net.minecraftforge.registries.ForgeRegistries;

@Mod.EventBusSubscriber(modid = CreateResourceGeodes.MOD_ID, bus = Mod.EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ForgeConfigSpec.Builder BUILDER = new ForgeConfigSpec.Builder();

    private static final ForgeConfigSpec.BooleanValue REPLACE_AE2_METEOR = BUILDER
            .comment("Should we replace the mystery box inside AE2 Meteors with the Catalyst?")
            .define("replaceAe2Meteor", false);

    private static final ForgeConfigSpec.IntValue MOVE_CATALYST_DISTANCE = BUILDER
            .comment("How many blocks should a Catalyst Block be moved when right-clicked with the Activator Wand?")
            .defineInRange("moveCatalystDistance", 1, 1, 32);

    private static final ForgeConfigSpec.IntValue ASURINE_CATALYST_PLACEMENT_SIZE = BUILDER
            .comment("Radius in blocks for generating Asurine around the Catalyst")
            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
            .defineInRange("asurineCatalystRadius", 6, 1,17);

    private static final ForgeConfigSpec.IntValue CRIMSITE_CATALYST_PLACEMENT_SIZE = BUILDER
            .comment("Radius in blocks for generating Crimsite around the Catalyst.")
            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
            .defineInRange("crimsiteCatalystRadius", 11, 3,27);

    private static final ForgeConfigSpec.IntValue OCHRUM_CATALYST_PLACEMENT_SIZE = BUILDER
            .comment("Radius in blocks for generating Ochrum around the Catalyst.")
            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
            .defineInRange("ochrumCatalystRadius", 6, 1,17);

    private static final ForgeConfigSpec.IntValue VERIDIUM_CATALYST_PLACEMENT_SIZE = BUILDER
            .comment("Radius in blocks for generating Veridium around the Catalyst.")
            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
            .defineInRange("veridiumCatalystRadius", 6, 1,17);

    private static final ForgeConfigSpec.IntValue SKYSTONE_CATALYST_PLACEMENT_SIZE = BUILDER
            .comment("Radius in blocks for generating Sky Stone around the Catalyst.")
            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
            .defineInRange("skystoneCatalystRadius", 6, 1,17);

    private static final ForgeConfigSpec.DoubleValue CATALYST_FILL_PERCENTAGE = BUILDER
            .comment("What percentage of the generated cube of blocks should actually be filled?")
            .comment("Warning!!!  Setting this percentage too high could have negative side effects, especially on a server!")
            .defineInRange("catalystFillPercentage", 0.12, 0.01, 1.00);

    private static final ForgeConfigSpec.BooleanValue CATALYST_MOVE_IGNORE_WATER = BUILDER
            .comment("Can a Catalyst move into a Water Source or Flowing Water block?")
            .define("catalystMoveIgnoreWater", false);

    static final ForgeConfigSpec SPEC = BUILDER.build();
//
    public static boolean replaceAe2Meteor;
    public static int moveCatalystDistance;
    public static int asurineCatalystRadius;
    public static int crimsiteCatalystRadius;
    public static int ochrumCatalystRadius;
    public static int veridiumCatalystRadius;
    public static int skystoneCatalystRadius;
    public static double catalystFillPercentage;
    public static boolean catalystMoveIgnoreWater;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        replaceAe2Meteor = REPLACE_AE2_METEOR.get();
        moveCatalystDistance = MOVE_CATALYST_DISTANCE.get();
        asurineCatalystRadius = ASURINE_CATALYST_PLACEMENT_SIZE.get();
        crimsiteCatalystRadius = CRIMSITE_CATALYST_PLACEMENT_SIZE.get();
        ochrumCatalystRadius = OCHRUM_CATALYST_PLACEMENT_SIZE.get();
        veridiumCatalystRadius = VERIDIUM_CATALYST_PLACEMENT_SIZE.get();
        skystoneCatalystRadius = SKYSTONE_CATALYST_PLACEMENT_SIZE.get();
        catalystFillPercentage = CATALYST_FILL_PERCENTAGE.get();
        catalystMoveIgnoreWater = CATALYST_MOVE_IGNORE_WATER.get();
    }
}
