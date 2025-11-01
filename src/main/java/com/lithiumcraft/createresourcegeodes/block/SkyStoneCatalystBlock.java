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

import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.neoforged.fml.ModList;

public class SkyStoneCatalystBlock extends CatalystBlock implements CatalystDataProvider {

    public SkyStoneCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getCatalystId() {
        return BuiltInRegistries.BLOCK.getKey(this);
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        // Ensure AE2 is Optional, Sky Stone Catalyst doesn't spawn in the wild without AE2, so this will never be an issue.
        return !ModList.get().isLoaded("ae2") ?
                Blocks.INFESTED_DEEPSLATE :
                BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("ae2", "sky_stone_block"));
    }
}
