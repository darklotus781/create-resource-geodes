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

package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

import java.util.Collection;

public class ModBlockEntities {

    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateResourceGeodes.MOD_ID);

    // --- Catalyst block entity type (covers all dynamic catalyst blocks) ---
    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CatalystBlockEntity>> CATALYST =
            BLOCK_ENTITY_TYPES.register("catalyst", () -> {
                // Collect every dynamic catalyst block registered in ModBlocks
                Collection<Block> catalystBlocks = ModBlocks.DYNAMIC_CATALYSTS.values()
                        .stream()
                        .map(deferred -> (Block) deferred.get())
                        .toList();

                if (catalystBlocks.isEmpty()) {
                    CreateResourceGeodes.LOGGER.warn("No dynamic catalyst blocks found when building BlockEntityType!");
                }

                return BlockEntityType.Builder
                        .of(CatalystBlockEntity::new, catalystBlocks.toArray(Block[]::new))
                        .build(null);
            });

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
