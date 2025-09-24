package com.lithiumcraft.createresourcegeodes.registry;

import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.TagKey;
import net.minecraft.world.level.block.Block;

public class CommonBlockTags {
    public static final TagKey<Block> RELOCATION_NOT_SUPPORTED =
        TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
            ResourceLocation.parse("c:relocation_not_supported"));

    public static final TagKey<Block> WITHER_IMMUNE =
            TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
                    ResourceLocation.parse("minecraft:wither_immune"));

    public static final TagKey<Block> DRAGON_IMMUNE =
            TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
                    ResourceLocation.parse("minecraft:dragon_immune"));

    public static final TagKey<Block> BLACKLISTED_SPATIAL =
            TagKey.create(net.minecraft.core.registries.Registries.BLOCK,
                    ResourceLocation.parse("ae2:blacklisted/spatial"));
}