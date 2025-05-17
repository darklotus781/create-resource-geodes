package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get());
    }

}