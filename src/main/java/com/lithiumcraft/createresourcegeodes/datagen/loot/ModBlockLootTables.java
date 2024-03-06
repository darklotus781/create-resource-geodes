package com.lithiumcraft.createresourcegeodes.datagen.loot;

import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.data.loot.BlockLootSubProvider;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.flag.FeatureFlags;
import net.minecraft.world.level.block.Block;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.Set;

public class ModBlockLootTables  extends BlockLootSubProvider {
    public ModBlockLootTables() {
        super(Set.of(), FeatureFlags.REGISTRY.allFlags());
    }

    @Override
    protected void generate() {
        this.dropSelf(ModBlocks.FAUX_ASURINE.get());
        this.dropSelf(ModBlocks.FAUX_CRIMSITE.get());
        this.dropSelf(ModBlocks.FAUX_OCHRUM.get());
        this.dropSelf(ModBlocks.FAUX_VERIDIUM.get());
        if (!ModList.get().isLoaded("ae2")) {
            this.dropSelf(ModBlocks.FAUX_SKY_STONE.get());
        } else {
            this.dropOther(ModBlocks.FAUX_SKY_STONE.get(), RegistryObject.create(new ResourceLocation("ae2:sky_stone_block"), ForgeRegistries.BLOCKS).get());
        }
    }

    @Override
    protected Iterable<Block> getKnownBlocks() {
        return ModBlocks.BLOCKS.getEntries().stream().map(RegistryObject::get)::iterator;
    }
}
