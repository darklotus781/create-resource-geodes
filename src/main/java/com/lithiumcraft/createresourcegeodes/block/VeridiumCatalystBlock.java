package com.lithiumcraft.createresourcegeodes.block;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

import java.util.Random;

public class VeridiumCatalystBlock extends CatalystBlock implements CatalystDataProvider {

    public VeridiumCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getCatalystId() {
        return BuiltInRegistries.BLOCK.getKey(this);
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        return BuiltInRegistries.BLOCK.get(ResourceLocation.fromNamespaceAndPath("create", "veridium"));
    }
}

