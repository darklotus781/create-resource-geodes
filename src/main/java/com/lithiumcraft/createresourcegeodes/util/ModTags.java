package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.tags.BlockTags;
import net.minecraft.tags.ItemTags;
import net.minecraft.tags.TagKey;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

public class ModTags {
    public static class Blocks {
//        public static final TagKey<Block> SKY_STONE_BLOCK = createTag("sky_stone_block");

        private static TagKey<Block> createTag(String name) {
            return BlockTags.create(ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, name));
        }
    }

    public static class Items {
//        public static final TagKey<Item> SKY_STONE_BLOCK = createTag("sky_stone_block");

        private static TagKey<Item> createTag(String name) {
            return ItemTags.create(ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, name));
        }
    }
}
