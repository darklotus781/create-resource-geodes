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