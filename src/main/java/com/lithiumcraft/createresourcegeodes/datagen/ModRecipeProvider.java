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

import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ASURINE_CATALYST.get())
//                .pattern("BBB")
//                .pattern("BWB")
//                .pattern("BBB")
//                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "asurine")))
//                .define('W', ModItems.CATALYST_CORE.get())
//                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
//                .save(recipeOutput.withConditions(new ModLoadedCondition("create")));
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRIMSITE_CATALYST.get())
//                .pattern("BBB")
//                .pattern("BWB")
//                .pattern("BBB")
//                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "crimsite")))
//                .define('W', ModItems.CATALYST_CORE.get())
//                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
//                .save(recipeOutput.withConditions(new ModLoadedCondition("create")));
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OCHRUM_CATALYST.get())
//                .pattern("BBB")
//                .pattern("BWB")
//                .pattern("BBB")
//                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "ochrum")))
//                .define('W', ModItems.CATALYST_CORE.get())
//                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
//                .save(recipeOutput.withConditions(new ModLoadedCondition("create")));
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VERIDIUM_CATALYST.get())
//                .pattern("BBB")
//                .pattern("BWB")
//                .pattern("BBB")
//                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "veridium")))
//                .define('W', ModItems.CATALYST_CORE.get())
//                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
//                .save(recipeOutput.withConditions(new ModLoadedCondition("create")));
//
//        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SKY_STONE_CATALYST.get())
//                .pattern("BBB")
//                .pattern("BWB")
//                .pattern("BBB")
//                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("ae2", "sky_stone_block")))
//                .define('W', ModItems.CATALYST_CORE.get())
//                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
//                .save(recipeOutput.withConditions(new ModLoadedCondition("ae2")));

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.CATALYST_CORE.get())
                .pattern("SDS")
                .pattern("DWD")
                .pattern("SDS")
                .define('D', Items.DIAMOND)
                .define('S', Items.NETHER_STAR)
                .define('W', ModItems.ACTIVATOR_WAND.get())
                .unlockedBy("has_wand", has(ModItems.ACTIVATOR_WAND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CATALYST_AGITATOR.get(), 8)
                .requires(ModItems.ACTIVATOR_WAND.get())
                .requires(Items.GUNPOWDER)
                .requires(Items.REDSTONE)
                .requires(Items.REDSTONE)
                .unlockedBy("has_wand", has(ModItems.ACTIVATOR_WAND))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CATALYST_AGITATOR_TIER_2.get(), 8)
                .requires(ModItems.ACTIVATOR_WAND.get())
                .requires(Items.GLOWSTONE_DUST)
                .requires(ModItems.CATALYST_AGITATOR.get())
                .requires(ModItems.CATALYST_AGITATOR.get())
                .unlockedBy("has_agitator_tier_1", has(ModItems.CATALYST_AGITATOR))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CATALYST_AGITATOR_TIER_3.get(), 8)
                .requires(ModItems.ACTIVATOR_WAND.get())
                .requires(Items.AMETHYST_SHARD)
                .requires(ModItems.CATALYST_AGITATOR_TIER_2.get())
                .requires(ModItems.CATALYST_AGITATOR_TIER_2.get())
                .unlockedBy("has_agitator_tier_2", has(ModItems.CATALYST_AGITATOR_TIER_2))
                .save(recipeOutput);

        ShapelessRecipeBuilder.shapeless(RecipeCategory.MISC, ModItems.CATALYST_AGITATOR_TIER_4.get(), 8)
                .requires(ModItems.ACTIVATOR_WAND.get())
                .requires(Items.ECHO_SHARD)
                .requires(ModItems.CATALYST_AGITATOR_TIER_3.get())
                .requires(ModItems.CATALYST_AGITATOR_TIER_3.get())
                .unlockedBy("has_agitator_tier_3", has(ModItems.CATALYST_AGITATOR_TIER_3))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModItems.ACTIVATOR_WAND.get())
                .pattern("  G")
                .pattern(" S ")
                .pattern("S  ")
                .define('G', ModItems.CATALYST_ACTIVATOR_WAND_GEM.get())
                .define('S', ModItems.CATALYST_ACTIVATOR_WAND_SHAFT.get())
                .unlockedBy("has_shaft", has(ModItems.CATALYST_ACTIVATOR_WAND_SHAFT))
                .unlockedBy("has_gem", has(ModItems.CATALYST_ACTIVATOR_WAND_GEM))
                .save(recipeOutput);
    }
}
