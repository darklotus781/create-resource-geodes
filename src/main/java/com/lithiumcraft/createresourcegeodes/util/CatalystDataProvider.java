package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;

public interface CatalystDataProvider {
    ResourceLocation getCatalystId();

    /** Required: Your hardcoded default block to use if the datapack entry is missing */
    Block getDefaultGeneratorBlock();

    /** Optional: fallback cooldown */
    default int getDefaultCooldown() {
        return 120;
    }

    /** Optional: fallback shape (if making shapes per-catalyst) */
    default CatalystShape getDefaultShape() {
        return CatalystShape.SPHERE;
    }

    /** Optional: fallback tier */
    default int getDefaultMinimumTier() {
        return 1;
    }

    default int getDefaultRadius() { return 4; }

    default float getDefaultFillPercentage() { return 0.35f; }

    /** Main accessor used by the item or block */
    default Block getGeneratorBlock(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        CatalystGeneratorDefinition def = registry.get(getCatalystId());
        return def != null ? def.generatorBlock() : getDefaultGeneratorBlock();
    }

    default int getCooldown(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        CatalystGeneratorDefinition def = registry.get(getCatalystId());
        return def != null ? def.cooldownTicks() : getDefaultCooldown();
    }

    default CatalystShape getShape(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.shape() : getDefaultShape();
    }

    default int getMinimumTier(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.minimumTier() : getDefaultMinimumTier();
    }

    default int getRadius(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.radius() : getDefaultRadius(); // fallback
    }

    default float getFillPercentage(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.fillPercentage() : getDefaultFillPercentage();
    }
}