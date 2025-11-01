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

import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalystRegistryCache {
    public static final List<CatalystGeneratorDefinition> DEFINITIONS = new ArrayList<>();
    public static final Map<ResourceLocation, CatalystGeneratorDefinition> BY_ID = new HashMap<>();

    public static void updateFromRegistry(Registry<CatalystGeneratorDefinition> registry) {
        DEFINITIONS.clear();
        BY_ID.clear();

        for (Map.Entry<ResourceKey<CatalystGeneratorDefinition>, CatalystGeneratorDefinition> entry : registry.entrySet()) {
            DEFINITIONS.add(entry.getValue());
            BY_ID.put(entry.getKey().location(), entry.getValue());
        }
    }

    public static void updateFromClient(Map<ResourceLocation, CatalystGeneratorDefinition> syncedDefs) {
        DEFINITIONS.clear();
        BY_ID.clear();
        for (var entry : syncedDefs.entrySet()) {
            DEFINITIONS.add(entry.getValue());
            BY_ID.put(entry.getKey(), entry.getValue());
        }
    }
}