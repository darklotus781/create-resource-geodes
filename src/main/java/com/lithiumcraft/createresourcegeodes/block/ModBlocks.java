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
import com.lithiumcraft.createresourcegeodes.util.CatalystBootstrap;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredBlock;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.nio.file.Path;
import java.util.LinkedHashMap;
import java.util.Map;
import java.util.Set;
import java.util.function.Supplier;

public class ModBlocks {
    public static final DeferredRegister.Blocks BLOCKS =
            DeferredRegister.createBlocks(CreateResourceGeodes.MOD_ID);

        public static final DeferredBlock<Block> DUMMY_CATALYST = registerBlock("dummy_catalyst",
            () -> new Block(BlockBehaviour.Properties.ofFullCopy(Blocks.BEDROCK)));

    /** Dynamically-registered catalyst blocks. */
    public static final Map<String, DeferredBlock<Block>> DYNAMIC_CATALYSTS = new LinkedHashMap<>();

    static {
        // Read names from config and register them as GenericCatalystBlock placeholders.
        // Avoid duplicating those already declared as explicit constants here.
        try {
            // We can get the config dir from FML paths (safe in static init):
            Path configDir = net.neoforged.fml.loading.FMLPaths.CONFIGDIR.get();
            Set<String> ids = CatalystBootstrap.blockIds(configDir);

            for (String name : ids) {
                DYNAMIC_CATALYSTS.put(name, registerCatalystPlaceholder(name));
            }
        } catch (Throwable t) {
            // Fail-closed: nothing dynamic if something goes wrong.
        }
    }

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
