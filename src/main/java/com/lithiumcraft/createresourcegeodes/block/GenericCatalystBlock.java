package com.lithiumcraft.createresourcegeodes.block;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;

public class GenericCatalystBlock extends CatalystBlock implements CatalystDataProvider {

    public GenericCatalystBlock(Properties properties) {
        super(properties);
    }

    @Override
    public ResourceLocation getCatalystId() {
        return BuiltInRegistries.BLOCK.getKey(this);
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        return Blocks.INFESTED_DEEPSLATE; // Can override per-block in registry if desired
    }

    @Override
    public CatalystShape getDefaultShape() {
        return CatalystShape.SPHERE;
    }

    @Override
    public int getDefaultCooldown() {
        return 120;
    }

    @Override
    public float getFillPercentage(ServerLevel level) {
        // You can keep using this method or just pull from registry, if you're not overriding defaults
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.fillPercentage() : 1.0f;
    }
}