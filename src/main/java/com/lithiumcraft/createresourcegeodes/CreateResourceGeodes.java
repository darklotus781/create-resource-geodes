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

package com.lithiumcraft.createresourcegeodes;

import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.block.entity.ModBlockEntities;
import com.lithiumcraft.createresourcegeodes.component.ModDataComponents;
import com.lithiumcraft.createresourcegeodes.item.ModCreativeModeTabs;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import com.lithiumcraft.createresourcegeodes.loot.ModLootModifiers;
import com.lithiumcraft.createresourcegeodes.network.ModNetwork;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.ModContainer;
import net.neoforged.fml.common.Mod;
import net.neoforged.fml.config.ModConfig;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.common.NeoForge;
import net.neoforged.neoforge.event.BuildCreativeModeTabContentsEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import org.slf4j.Logger;


@Mod(CreateResourceGeodes.MOD_ID)
public class CreateResourceGeodes {
    public static final String MOD_ID = "createresourcegeodes";
    public static final Logger LOGGER = LogUtils.getLogger();

    public CreateResourceGeodes(IEventBus modEventBus, ModContainer modContainer) {
        modEventBus.addListener(this::commonSetup);

        ModCreativeModeTabs.register(modEventBus);

        ModItems.register(modEventBus);
        ModBlocks.register(modEventBus);

        ModDataComponents.register(modEventBus);

        ModBlockEntities.register(modEventBus);

        ModSounds.register(modEventBus);

        ModLootModifiers.register(modEventBus);

        ModRegistries.register(modEventBus);

        modEventBus.addListener(ModNetwork::registerPackets);

        modContainer.registerConfig(ModConfig.Type.COMMON, Config.SPEC);
    }

    private void commonSetup(final FMLCommonSetupEvent event) {
        CatalystDebugLogger.register();
    }

    public class CatalystDebugLogger {
        public static void register() {
            NeoForge.EVENT_BUS.register(CatalystDebugLogger.class);
        }

        @SubscribeEvent
        public static void onServerStarted(ServerStartedEvent event) {
            var level = event.getServer().overworld();
            var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);

//            System.out.println("[CatalystDebug] Registry size: " + registry.size());

//            System.out.println("[CatalystDebug] === Catalyst Datapack Entries ===");
//            registry.keySet().forEach(key -> System.out.println(" - " + key));
        }
    }

    public static ResourceLocation rl(String id) {
        return ResourceLocation.fromNamespaceAndPath(MOD_ID, id);
    }

}