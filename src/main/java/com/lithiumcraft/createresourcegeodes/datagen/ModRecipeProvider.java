package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import com.lithiumcraft.createresourcegeodes.util.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.data.PackOutput;
import net.minecraft.data.recipes.*;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.crafting.*;
import net.minecraft.world.level.ItemLike;
import net.neoforged.fml.ModList;
import net.neoforged.neoforge.common.conditions.IConditionBuilder;
import net.neoforged.neoforge.common.conditions.ModLoadedCondition;

import java.util.List;
import java.util.concurrent.CompletableFuture;

public class ModRecipeProvider extends RecipeProvider implements IConditionBuilder {
    public ModRecipeProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> registries) {
        super(output, registries);
    }

    @Override
    protected void buildRecipes(RecipeOutput recipeOutput) {
        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.ASURINE_CATALYST.get())
                .pattern("BBB")
                .pattern("BWB")
                .pattern("BBB")
                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "asurine")))
                .define('W', ModItems.CATALYST_CORE.get())
                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.CRIMSITE_CATALYST.get())
                .pattern("BBB")
                .pattern("BWB")
                .pattern("BBB")
                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "crimsite")))
                .define('W', ModItems.CATALYST_CORE.get())
                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.OCHRUM_CATALYST.get())
                .pattern("BBB")
                .pattern("BWB")
                .pattern("BBB")
                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "ochrum")))
                .define('W', ModItems.CATALYST_CORE.get())
                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.VERIDIUM_CATALYST.get())
                .pattern("BBB")
                .pattern("BWB")
                .pattern("BBB")
                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "veridium")))
                .define('W', ModItems.CATALYST_CORE.get())
                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
                .save(recipeOutput);

        ShapedRecipeBuilder.shaped(RecipeCategory.MISC, ModBlocks.SKY_STONE_CATALYST.get())
                .pattern("BBB")
                .pattern("BWB")
                .pattern("BBB")
                .define('B', BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("ae2", "sky_stone_block")))
                .define('W', ModItems.CATALYST_CORE.get())
                .unlockedBy("has_core", has(ModItems.CATALYST_CORE))
                .save(recipeOutput.withConditions(new ModLoadedCondition("ae2")));

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
    }
}
