package com.lithiumcraft.createresourcegeodes.compat.jei;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import mezz.jei.api.constants.VanillaTypes;
import mezz.jei.api.gui.builder.IRecipeLayoutBuilder;
import mezz.jei.api.gui.drawable.IDrawable;
import mezz.jei.api.gui.ingredient.IRecipeSlotsView;
import mezz.jei.api.recipe.IFocusGroup;
import mezz.jei.api.recipe.RecipeIngredientRole;
import mezz.jei.api.recipe.category.IRecipeCategory;
import mezz.jei.api.helpers.IGuiHelper;
import mezz.jei.api.recipe.RecipeType;
import net.minecraft.client.Minecraft;
import net.minecraft.client.gui.Font;
import net.minecraft.client.gui.GuiGraphics;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.item.*;
import net.minecraft.world.level.block.Block;

public class CatalystRecipeCategory implements IRecipeCategory<CatalystRecipe> {
    public static final ResourceLocation UID = ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "catalyst_generation");
    public static final RecipeType<CatalystRecipe> TYPE = RecipeType.create(CreateResourceGeodes.MOD_ID, "catalyst_generation", CatalystRecipe.class);
    public static final ResourceLocation TEXTURE = ResourceLocation.fromNamespaceAndPath("minecraft", "textures/gui/container/anvil.png");

    private static final int WIDTH = 150;
    private static final int HEIGHT = 65;
    private final IDrawable icon;
    private final IDrawable background;

    public CatalystRecipeCategory(IGuiHelper guiHelper) {
        this.icon = guiHelper.createDrawableIngredient(VanillaTypes.ITEM_STACK, new ItemStack(ModBlocks.SKY_STONE_CATALYST.asItem()));
        this.background = guiHelper.createDrawable(TEXTURE,14 ,38, 140, 36);

//        System.out.println("[JEI] CatalystRecipeCategory constructed");
    }

    @Override
    public IDrawable getBackground() {
        return background;
    }

    @Override
    public int getWidth() {
        return WIDTH;
    }

    @Override
    public int getHeight() {
        return HEIGHT;
    }

    @Override
    public RecipeType<CatalystRecipe> getRecipeType() {
        return TYPE;
    }

    @Override
    public Component getTitle() {
        return Component.translatable("jei.createresourcegeodes.category.catalyst_generation");
    }

    @Override
    public IDrawable getIcon() {
        return icon;
    }

    @Override
    public void setRecipe(IRecipeLayoutBuilder builder, CatalystRecipe recipe, IFocusGroup focuses) {
        // Input: Agitator item
        builder.addSlot(RecipeIngredientRole.INPUT, 13, 9) // left slot
                .addItemStack(recipe.getAgitatorItem());

        // Input: Catalyst Block
        builder.addSlot(RecipeIngredientRole.INPUT, 62, 9) // right slot
                .addItemStack(recipe.getCatalystItem());

        // Output: Generated block (as item or fallback)
        Block generatorBlock = recipe.getGeneratorBlock();
        Item blockItem = generatorBlock.asItem();

        if (blockItem == Items.AIR || blockItem == null) {
            blockItem = Items.BARRIER;
        }

        ItemStack displayStack = new ItemStack(blockItem);

        builder.addSlot(RecipeIngredientRole.OUTPUT, 120, 9) // output
                .addItemStack(displayStack);
    }

    @Override
    public void draw(CatalystRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
        Minecraft mc = Minecraft.getInstance();
        Font font = mc.font;

        String text = "Tier " + recipe.getTier() + " Agitator Required";
        int x = 11;
        int y = 57;

        guiGraphics.drawString(font, text, x, y, 0x80FF20, false); // goldish green
    }

//    @Override
//    public void draw(CatalystRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        Font font = Minecraft.getInstance().font;
//
//        // Draw a clean arrow between slots, vertically centered to match item slot
//        guiGraphics.drawString(font, "→", 44, 14, 0xFFFFFF, true);
//    }

//    @Override
//    public void draw(CatalystRecipe recipe, IRecipeSlotsView recipeSlotsView, GuiGraphics guiGraphics, double mouseX, double mouseY) {
//        Minecraft mc = Minecraft.getInstance();
//        Font font = mc.font;
//
//        String line1 = String.format(
//                "%s → %s",
//                recipe.getCatalystItem().getHoverName().getString(),
//                BuiltInRegistries.BLOCK.getKey(recipe.getGeneratorBlock()).getPath().replace("_", " ")
//        );
//
//        guiGraphics.drawString(font, line1, 0, 28, 0x555555, false);
//    }
}
