package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

@Deprecated
public class CatalystRegistryHelper {

    public static Block getGeneratorBlock(ServerLevel level, ResourceLocation id) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        CatalystGeneratorDefinition def = registry.get(id);
        return def != null ? def.generatorBlock() : null;
    }

    public static int getCooldown(ServerLevel level, ResourceLocation id) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        CatalystGeneratorDefinition def = registry.get(id);
        return def != null ? def.cooldownTicks() : 120;
    }
}