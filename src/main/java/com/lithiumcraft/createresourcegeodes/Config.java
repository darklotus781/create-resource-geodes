package com.lithiumcraft.createresourcegeodes;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.config.ModConfigEvent;
import net.neoforged.neoforge.common.ModConfigSpec;

@EventBusSubscriber(modid = CreateResourceGeodes.MOD_ID, bus = EventBusSubscriber.Bus.MOD)
public class Config
{
    private static final ModConfigSpec.Builder BUILDER = new ModConfigSpec.Builder();

    private static final ModConfigSpec.BooleanValue REPLACE_AE2_METEOR = BUILDER
            .comment("Should we replace the mystery box inside AE2 Meteors with the Catalyst?")
            .define("replaceAe2Meteor", false);

    private static final ModConfigSpec.BooleanValue CATALYST_WAND_DURABILITY = BUILDER
            .comment("Does the Catalyst Wand have durability?")
            .define("catalystWandDurability", true);

    private static final ModConfigSpec.IntValue MOVE_CATALYST_DISTANCE = BUILDER
            .comment("How many blocks should a Catalyst Block be moved when right-clicked with the Activator Wand?")
            .defineInRange("moveCatalystDistance", 3, 1, 32);

    private static final ModConfigSpec.IntValue CATALYST_BLOCKS_PER_TICK = BUILDER
            .comment("How many blocks should be generated per tick, there's 20 ticks in a second, so keep that in mind!")
            .defineInRange("catalystBlocksPerTick", 5, 1, 100);

//    private static final ModConfigSpec.IntValue CATALYST_PLACEMENT_SIZE = BUILDER
//            .comment("Radius in blocks for generating BLocks around the Catalyst")
//            .comment("Warning!!!  Setting this diameter too large could have negative side effects, especially on a server!")
//            .defineInRange("catalystRadius", 6, 1,17);

//    private static final ModConfigSpec.DoubleValue CATALYST_FILL_PERCENTAGE = BUILDER
//            .comment("What percentage of the generated cube of blocks should actually be filled?")
//            .comment("Warning!!!  Setting this percentage too high could have negative side effects, especially on a server!")
//            .defineInRange("catalystFillPercentage", 0.7, 0.1, 1.0);

    private static final ModConfigSpec.BooleanValue CATALYST_MOVE_IGNORE_WATER = BUILDER
            .comment("Can a Catalyst move into a Water Source or Flowing Water block?")
            .define("catalystMoveIgnoreWater", true);

//    private static final ModConfigSpec.EnumValue<CatalystShape> CATALYST_SHAPE = BUILDER
//            .comment("The shape used when generating blocks around the Catalyst.")
//            .defineEnum("catalystShape", CatalystShape.SPHERE);

    static final ModConfigSpec SPEC = BUILDER.build();
//
    public static boolean replaceAe2Meteor;
    public static boolean catalystWandDurability;
    public static int moveCatalystDistance;
//    public static int catalystRadius;
    public static int catalystBlocksPerTick;
//    public static double catalystFillPercentage;
    public static boolean catalystMoveIgnoreWater;
//    public static CatalystShape catalystShape;


    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof String itemName && BuiltInRegistries.ITEM.containsKey(ResourceLocation.parse(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        replaceAe2Meteor = REPLACE_AE2_METEOR.get();
        catalystWandDurability = CATALYST_WAND_DURABILITY.get();
        moveCatalystDistance = MOVE_CATALYST_DISTANCE.get();
//        catalystRadius = CATALYST_PLACEMENT_SIZE.get();
//        catalystFillPercentage = CATALYST_FILL_PERCENTAGE.get();
        catalystMoveIgnoreWater = CATALYST_MOVE_IGNORE_WATER.get();
//        catalystShape = CATALYST_SHAPE.get();
        catalystBlocksPerTick = CATALYST_BLOCKS_PER_TICK.get();
    }
}
