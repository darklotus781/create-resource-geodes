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

package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.Map;
import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateResourceGeodes.MOD_ID);

    public static final Supplier<CreativeModeTab> GEODE_ITEM_TABS = CREATIVE_MODE_TAB.register("createresourcegeodes_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ACTIVATOR_WAND.get()))
                    .title(Component.translatable("creativetab.createresourcegeodes_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        ModBlocks.DYNAMIC_CATALYSTS.entrySet().stream()
                                .sorted(Map.Entry.comparingByKey())
                                .forEach(entry -> output.accept(entry.getValue().get().asItem()));
                        output.accept(ModItems.ACTIVATOR_WAND);
                        output.accept(ModItems.CATALYST_AGITATOR);
                        output.accept(ModItems.CATALYST_AGITATOR_TIER_2);
                        output.accept(ModItems.CATALYST_AGITATOR_TIER_3);
                        output.accept(ModItems.CATALYST_AGITATOR_TIER_4);
                        output.accept(ModItems.CATALYST_CORE);
                        output.accept(ModItems.CATALYST_ACTIVATOR_WAND_SHAFT);
                        output.accept(ModItems.CATALYST_ACTIVATOR_WAND_GEM);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
