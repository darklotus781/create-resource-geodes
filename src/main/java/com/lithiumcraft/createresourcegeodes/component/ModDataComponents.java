package com.lithiumcraft.createresourcegeodes.component;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.mojang.serialization.Codec;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponentType;
import net.minecraft.nbt.StringTag;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.UnaryOperator;

public class ModDataComponents {
    public static final DeferredRegister<DataComponentType<?>> DATA_COMPONENT_TYPES =
            DeferredRegister.createDataComponents(CreateResourceGeodes.MOD_ID);

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<BlockPos>> COORDINATES =
            register("coordinates", builder -> builder.persistent(BlockPos.CODEC));

    public static final DeferredHolder<DataComponentType<?>, DataComponentType<String>> WAND_MODE =
            register("wand_mode", builder -> builder.persistent(Codec.STRING));


    private static <T> DeferredHolder<DataComponentType<?>, DataComponentType<T>> register(String name, UnaryOperator<DataComponentType.Builder<T>> builderOperator) {
        return DATA_COMPONENT_TYPES.register(name, () -> builderOperator.apply(DataComponentType.builder()).build());
    }

    public static void register(IEventBus eventBus) {
        DATA_COMPONENT_TYPES.register(eventBus);
    }
}