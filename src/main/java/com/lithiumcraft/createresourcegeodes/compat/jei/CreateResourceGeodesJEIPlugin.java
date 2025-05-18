package com.lithiumcraft.createresourcegeodes.compat.jei;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.registry.CatalystRegistryCache;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import mezz.jei.api.IModPlugin;
import mezz.jei.api.JeiPlugin;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.registration.IRecipeCategoryRegistration;
import mezz.jei.api.registration.IRecipeRegistration;
import net.minecraft.ChatFormatting;
import net.minecraft.client.Minecraft;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;

import java.util.List;
import java.util.Objects;
import java.util.function.Supplier;

@JeiPlugin
public class CreateResourceGeodesJEIPlugin implements IModPlugin {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "jei_plugin");

    @Override
    public ResourceLocation getPluginUid() {
        return ID;
    }

    @Override
    public void registerCategories(IRecipeCategoryRegistration registration) {
//        System.out.println("[JEI] Registering CatalystRecipeCategory");
        registration.addRecipeCategories(new CatalystRecipeCategory(registration.getJeiHelpers().getGuiHelper()));
    }

    @Override
    public void registerRecipes(IRecipeRegistration registration) {
//        System.out.println("[JEI] CatalystRegistryCache contains: " + CatalystRegistryCache.BY_ID.size() + " entries");

        if (CatalystRegistryCache.BY_ID.isEmpty()) {
//            System.out.println("[JEI] CatalystRegistryCache is empty — rebuilding from client registry");

            var level = Minecraft.getInstance().level;
            if (level != null) {
                try {
                    var access = Minecraft.getInstance().player.connection.registryAccess();
                    Registry<CatalystGeneratorDefinition> registry = access.registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
                    CatalystRegistryCache.updateFromRegistry(registry);

//                    System.out.println("[JEI] Rebuilt CatalystRegistryCache from client registry: " + CatalystRegistryCache.BY_ID.size() + " entries");
                } catch (Exception e) {
//                    System.err.println("[JEI] ❌ Failed to rebuild CatalystRegistryCache: " + e.getMessage());
                    e.printStackTrace();
                }
            } else {
//                System.out.println("[JEI] ⚠ Minecraft level is null — cannot rebuild CatalystRegistryCache yet");
            }
        }

        List<CatalystRecipe> recipes = ModBlocks.BLOCKS.getEntries().stream()
                .map(Supplier::get)
//                .peek(block -> System.out.println("[JEI] Checking block: " + BuiltInRegistries.BLOCK.getKey(block)))
                .filter(block -> block instanceof CatalystDataProvider)
                .map(block -> {
                    CatalystDataProvider provider = (CatalystDataProvider) block;
                    ResourceLocation id = provider.getCatalystId();

                    CatalystGeneratorDefinition def = CatalystRegistryCache.BY_ID.get(id);
                    if (def == null) {
                        def = new CatalystGeneratorDefinition(
                                provider.getDefaultGeneratorBlock(),
                                provider.getDefaultCooldown(),
                                provider.getDefaultShape(),
                                provider.getDefaultRadius(),
                                provider.getDefaultFillPercentage(),
                                provider.getDefaultMinimumTier()
                        );
//                        System.out.println("[JEI] Using fallback definition for: " + id);
                    }

                    Item blockItem = block.asItem();
                    if (blockItem == Items.AIR || blockItem == null) {
//                        System.out.println("[JEI] ❌ Skipping: Catalyst block has no item: " + BuiltInRegistries.BLOCK.getKey(block));
                        return null;
                    }

                    Item genItem = def.generatorBlock().asItem();
                    if (genItem == Items.AIR || genItem == null) {
//                        System.out.println("[JEI] ❌ Skipping: Generator block has no item: " + BuiltInRegistries.BLOCK.getKey(def.generatorBlock()));
                        return null;
                    }

//                    System.out.println("[JEI] ✅ Valid recipe: " + id + " → " + BuiltInRegistries.ITEM.getKey(genItem));
                    return new CatalystRecipe(new ItemStack(blockItem), def);
                })
                .filter(Objects::nonNull)
                .toList();

        registration.addRecipes(CatalystRecipeCategory.TYPE, recipes);

        List<ItemStack> catalystItems = recipes.stream()
                .map(CatalystRecipe::getCatalystItem)
                .filter(stack -> !stack.isEmpty())
                .distinct()
                .toList();

        if (!catalystItems.isEmpty()) {
            for (CatalystRecipe recipe : recipes) {
                ItemStack stack = recipe.getCatalystItem();

                String shapeName = recipe.getShape().name().toLowerCase();
                shapeName = shapeName.substring(0, 1).toUpperCase() + shapeName.substring(1);
                String generatorName = BuiltInRegistries.BLOCK.getKey(recipe.getGeneratorBlock()).getPath().replace("_", " ");
                generatorName = generatorName.substring(0, 1).toUpperCase() + generatorName.substring(1);

                Component info = Component.literal("")
                        .append(Component.literal("Generates: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(generatorName)
                                .withStyle(ChatFormatting.DARK_PURPLE))
                        .append(Component.literal("\nTier Required: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal("Tier " + recipe.getTier())
                                .withStyle(ChatFormatting.DARK_PURPLE))
                        .append(Component.literal("\nCooldown: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal((recipe.getCooldownTicks() / 20) + " seconds")
                                .withStyle(ChatFormatting.DARK_PURPLE))
                        .append(Component.literal("\nRadius: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(recipe.getRadius() + "")
                                .withStyle(ChatFormatting.DARK_PURPLE))
                        .append(Component.literal("\nFill: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(Math.round(recipe.getFillPercentage() * 100f) + "%")
                                .withStyle(ChatFormatting.DARK_PURPLE))
                        .append(Component.literal("\nShape: ")
                                .withStyle(ChatFormatting.DARK_GRAY))
                        .append(Component.literal(shapeName)
                                .withStyle(ChatFormatting.DARK_PURPLE));

                registration.addIngredientInfo(List.of(stack), VanillaTypes.ITEM_STACK, info);
            }
        }

        // Optional: hardcoded test recipe for validation
        /*
        CatalystRecipe test = new CatalystRecipe(
                new ItemStack(Items.STICK),
                new CatalystGeneratorDefinition(Blocks.DIAMOND_BLOCK, 120, CatalystShape.CUBE, 3, 1.0f, 1)
        );
        registration.addRecipes(CatalystRecipeCategory.TYPE, List.of(test));
        */
    }
}
