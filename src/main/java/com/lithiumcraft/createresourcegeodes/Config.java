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

    static final ForgeConfigSpec SPEC = BUILDER.build();
//
    public static boolean replaceAe2Meteor;

    private static boolean validateItemName(final Object obj)
    {
        return obj instanceof final String itemName && ForgeRegistries.ITEMS.containsKey(new ResourceLocation(itemName));
    }

    @SubscribeEvent
    static void onLoad(final ModConfigEvent event)
    {
        replaceAe2Meteor = REPLACE_AE2_METEOR.get();
    }
}
