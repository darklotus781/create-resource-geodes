package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.minecraftforge.common.data.BlockTagsProvider;
import net.minecraftforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagGenerator extends BlockTagsProvider {
    public ModBlockTagGenerator(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider pProvider) {
        this.tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get());

        this.tag(BlockTags.MINEABLE_WITH_PICKAXE)
                .add(ModBlocks.FAUX_ASURINE.get(),
                        ModBlocks.FAUX_CRIMSITE.get(),
                        ModBlocks.FAUX_OCHRUM.get(),
                        ModBlocks.FAUX_VERIDIUM.get(),
                        ModBlocks.FAUX_SKY_STONE.get());

        this.tag(BlockTags.NEEDS_IRON_TOOL)
                .add(ModBlocks.FAUX_ASURINE.get(),
                        ModBlocks.FAUX_CRIMSITE.get(),
                        ModBlocks.FAUX_OCHRUM.get(),
                        ModBlocks.FAUX_VERIDIUM.get(),
                        ModBlocks.FAUX_SKY_STONE.get());
    }
}