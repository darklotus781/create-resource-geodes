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

package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import com.lithiumcraft.createresourcegeodes.loot.AddItemModifier;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.storage.loot.predicates.LootItemBlockStatePropertyCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.minecraft.world.level.storage.loot.predicates.LootItemRandomChanceCondition;
import net.neoforged.neoforge.common.data.GlobalLootModifierProvider;
import net.neoforged.neoforge.common.loot.LootTableIdCondition;

import java.util.concurrent.CompletableFuture;

public class ModGlobalLootModifierProvider extends GlobalLootModifierProvider {
    public ModGlobalLootModifierProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries, CreateResourceGeodes.MOD_ID);
    }

    @Override
    protected void start() {
//        this.add("catalyst_activator_wand_gem_from_abandoned_mineshafts",
//                new AddItemModifier(new LootItemCondition[]{
//                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("chests/abandoned_mineshaft")).build(),
//                        LootItemRandomChanceCondition.randomChance(0.35f).build()}, ModItems.ACTIVATOR_WAND_GEM.get()));
//
//        this.add("catalyst_activator_wand_shaft_from_warden",
//                new AddItemModifier(new LootItemCondition[]{
//                        new LootTableIdCondition.Builder(ResourceLocation.withDefaultNamespace("entities/warden")).build(),
//                        LootItemRandomChanceCondition.randomChance(0.25f).build()}, ModItems.ACTIVATOR_WAND_SHAFT.get()));
    }
}
