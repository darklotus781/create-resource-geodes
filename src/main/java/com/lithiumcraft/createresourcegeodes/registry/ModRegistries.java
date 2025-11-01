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

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class ModRegistries {
    public static final ResourceKey<Registry<CatalystGeneratorDefinition>> CATALYST_DEFINITION_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "catalysts"));

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModRegistries::registerDataPackRegistries);
    }

    public static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(CATALYST_DEFINITION_KEY, CatalystGeneratorDefinition.CODEC, CatalystGeneratorDefinition.CODEC);
    }
}
