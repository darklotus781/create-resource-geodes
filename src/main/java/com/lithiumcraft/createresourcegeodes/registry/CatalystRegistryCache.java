package com.lithiumcraft.createresourcegeodes.registry;

import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class CatalystRegistryCache {
    public static final List<CatalystGeneratorDefinition> DEFINITIONS = new ArrayList<>();
    public static final Map<ResourceLocation, CatalystGeneratorDefinition> BY_ID = new HashMap<>();

    public static void updateFromRegistry(Registry<CatalystGeneratorDefinition> registry) {
        DEFINITIONS.clear();
        BY_ID.clear();

        for (Map.Entry<ResourceKey<CatalystGeneratorDefinition>, CatalystGeneratorDefinition> entry : registry.entrySet()) {
            DEFINITIONS.add(entry.getValue());
            BY_ID.put(entry.getKey().location(), entry.getValue());
        }
    }

    public static void updateFromClient(Map<ResourceLocation, CatalystGeneratorDefinition> syncedDefs) {
        DEFINITIONS.clear();
        BY_ID.clear();
        for (var entry : syncedDefs.entrySet()) {
            DEFINITIONS.add(entry.getValue());
            BY_ID.put(entry.getKey(), entry.getValue());
        }
    }
}