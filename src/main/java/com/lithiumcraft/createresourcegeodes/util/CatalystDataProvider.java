package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
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

    /** Main accessor used by the item or block */
    default Block getGeneratorBlock(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        CatalystGeneratorDefinition def = registry.get(getCatalystId());
        return def != null ? def.generatorBlock() : getDefaultGeneratorBlock();
    }

//    default Block getGeneratorBlock(ServerLevel level) {
//        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
//        var id = getCatalystId();
//
//        System.out.println("[Catalyst] Looking up: " + id);
//        if (!registry.containsKey(id)) {
//            System.out.println("[Catalyst] Registry does NOT contain: " + id);
//        } else {
//            System.out.println("[Catalyst] Registry contains: " + id);
//        }
//
//        CatalystGeneratorDefinition def = registry.get(id);
//
//        if (def != null) {
//            System.out.println("[Catalyst] Loaded from datapack: " + id + " -> " + def.generatorBlock());
//            return def.generatorBlock();
//        } else {
//            System.out.println("[Catalyst] Fallback used: " + id);
//            return getDefaultGeneratorBlock();
//        }
//    }

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
        return def != null ? def.radius() : 3; // fallback
    }

    default float getFillPercentage(ServerLevel level) {
        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(getCatalystId());
        return def != null ? def.fillPercentage() : 1.0f;
    }
}