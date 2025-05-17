package com.lithiumcraft.createresourcegeodes.registry;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import net.minecraft.core.Registry;
import net.minecraft.resources.ResourceKey;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DataPackRegistryEvent;

public class ModRegistries {
    public static final ResourceKey<Registry<CatalystGeneratorDefinition>> CATALYST_DEFINITION_KEY =
            ResourceKey.createRegistryKey(ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "catalysts"));

    public static void register(IEventBus modEventBus) {
        modEventBus.addListener(ModRegistries::registerDataPackRegistries);
    }

    public static void registerDataPackRegistries(DataPackRegistryEvent.NewRegistry event) {
        event.dataPackRegistry(CATALYST_DEFINITION_KEY, CatalystGeneratorDefinition.CODEC, CatalystGeneratorDefinition.CODEC);
    }
}
