package com.lithiumcraft.createresourcegeodes.block;

import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.level.block.Block;

public class OchrumCatalystBlock extends CatalystBlock implements CatalystDataProvider {

    public OchrumCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getCatalystId() {
        return BuiltInRegistries.BLOCK.getKey(this);
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "ochrum"));
    }
}

