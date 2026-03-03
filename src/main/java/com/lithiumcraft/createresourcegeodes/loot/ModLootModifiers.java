package com.lithiumcraft.createresourcegeodes.loot;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.mojang.serialization.MapCodec;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.NeoForgeRegistries;

import java.util.function.Supplier;

public final class ModLootModifiers {
    private ModLootModifiers() {}

    public static final DeferredRegister<MapCodec<? extends IGlobalLootModifier>> LOOT_MODIFIERS =
            DeferredRegister.create(NeoForgeRegistries.GLOBAL_LOOT_MODIFIER_SERIALIZERS, CreateResourceGeodes.MOD_ID);

    public static final Supplier<MapCodec<InjectLootModifier>> INJECT_LOOT =
            LOOT_MODIFIERS.register("inject_loot", () -> InjectLootModifier.CODEC);

    public static void register(IEventBus modEventBus) {
        LOOT_MODIFIERS.register(modEventBus);
    }
}
