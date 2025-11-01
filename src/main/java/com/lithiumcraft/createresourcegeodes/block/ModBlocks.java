/*
 * create-resource-geodes
 * Copyright (c) 2025 DarkLotus (DarkLotus781) / LithiumCraft
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

package com.lithiumcraft.createresourcegeodes.block;


import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateResourceGeodes.MOD_ID);

    public static final DeferredBlock<Block> ASURINE_CATALYST = registerBlock("asurine_catalyst",
            () -> new AsurineCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.COLOR_BLUE).noLootTable().randomTicks()));
    public static final DeferredBlock<Block> CRIMSITE_CATALYST = registerBlock("crimsite_catalyst",
            () -> new CrimsiteCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.COLOR_RED).noLootTable().randomTicks()));
    public static final DeferredBlock<Block> OCHRUM_CATALYST = registerBlock("ochrum_catalyst",
            () -> new OchrumCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.TERRACOTTA_YELLOW).noLootTable().randomTicks()));
    public static final DeferredBlock<Block> VERIDIUM_CATALYST = registerBlock("veridium_catalyst",
            () -> new VeridiumCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.WARPED_NYLIUM).noLootTable().randomTicks()));
    public static final DeferredBlock<Block> SKY_STONE_CATALYST = registerBlock("sky_stone_catalyst",
            () -> new SkyStoneCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.TERRACOTTA_BLACK).noLootTable().randomTicks()));

    // Generic Catalyst Placeholders for Modpack makers:
    public static final DeferredBlock<Block> GENERIC_CATALYST_1 = registerCatalystPlaceholder("generic_catalyst_1");
    public static final DeferredBlock<Block> GENERIC_CATALYST_2 = registerCatalystPlaceholder("generic_catalyst_2");
    public static final DeferredBlock<Block> GENERIC_CATALYST_3 = registerCatalystPlaceholder("generic_catalyst_3");
    public static final DeferredBlock<Block> GENERIC_CATALYST_4 = registerCatalystPlaceholder("generic_catalyst_4");
    public static final DeferredBlock<Block> GENERIC_CATALYST_5 = registerCatalystPlaceholder("generic_catalyst_5");
    public static final DeferredBlock<Block> GENERIC_CATALYST_6 = registerCatalystPlaceholder("generic_catalyst_6");
    public static final DeferredBlock<Block> GENERIC_CATALYST_7 = registerCatalystPlaceholder("generic_catalyst_7");
    public static final DeferredBlock<Block> GENERIC_CATALYST_8 = registerCatalystPlaceholder("generic_catalyst_8");
    public static final DeferredBlock<Block> GENERIC_CATALYST_9 = registerCatalystPlaceholder("generic_catalyst_9");
    public static final DeferredBlock<Block> GENERIC_CATALYST_10 = registerCatalystPlaceholder("generic_catalyst_10");
    public static final DeferredBlock<Block> GENERIC_CATALYST_11 = registerCatalystPlaceholder("generic_catalyst_11");
    public static final DeferredBlock<Block> GENERIC_CATALYST_12 = registerCatalystPlaceholder("generic_catalyst_12");


    private static <T extends Block> DeferredBlock<T> registerBlock(String name, Supplier<T> block) {
        DeferredBlock<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, DeferredBlock<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    private static DeferredBlock<Block> registerCatalystPlaceholder(String name) {
        return registerBlock(name, () ->
                new GenericCatalystBlock(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)
                        .sound(SoundType.AMETHYST)
                        .lightLevel(s -> 10)
                        .mapColor(MapColor.COLOR_GRAY)
                        .noLootTable()
                        .randomTicks())
        );
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
