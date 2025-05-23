package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        handheldItem(ModItems.ACTIVATOR_WAND);
        simpleItem(ModItems.CATALYST_AGITATOR);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_2);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_3);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_4);
        simpleItem(ModItems.CATALYST_ACTIVATOR_WAND_GEM);
        simpleItem(ModItems.CATALYST_ACTIVATOR_WAND_SHAFT);
        simpleItem(ModItems.CATALYST_CORE);
    }

    private ItemModelBuilder simpleItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "item/" + item.getId().getPath()));
    }
}