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
import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GenericCatalystBlock extends CatalystBlock implements CatalystDataProvider {

    public GenericCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getCatalystId() {
        return BuiltInRegistries.BLOCK.getKey(this);
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        return Blocks.INFESTED_DEEPSLATE; // Can override per-block in registry if desired
    }

    @Override
    public CatalystShape getDefaultShape() {
        return CatalystShape.SPHERE;
    }

    @Override
    public int getDefaultCooldown() {
        return 120;
    }

    @Override
    public float getFillPercentage(ServerLevel level) {
        // You can keep using this method or just pull from registry, if you're not overriding defaults
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.fillPercentage() : 1.0f;
    }
}