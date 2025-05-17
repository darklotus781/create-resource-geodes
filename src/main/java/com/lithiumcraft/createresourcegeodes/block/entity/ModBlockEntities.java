package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.neoforged.neoforge.registries.DeferredRegister;
import net.neoforged.neoforge.registries.DeferredHolder;
import net.neoforged.bus.api.IEventBus;

public class ModBlockEntities {
    public static final DeferredRegister<BlockEntityType<?>> BLOCK_ENTITY_TYPES =
            DeferredRegister.create(BuiltInRegistries.BLOCK_ENTITY_TYPE, CreateResourceGeodes.MOD_ID);

    public static final DeferredHolder<BlockEntityType<?>, BlockEntityType<CatalystBlockEntity>> CATALYST =
            BLOCK_ENTITY_TYPES.register("catalyst", () ->
                    BlockEntityType.Builder.of(CatalystBlockEntity::new,
                            ModBlocks.ASURINE_CATALYST.get(),
                            ModBlocks.CRIMSITE_CATALYST.get(),
                            ModBlocks.OCHRUM_CATALYST.get(),
                            ModBlocks.VERIDIUM_CATALYST.get(),
                            ModBlocks.SKY_STONE_CATALYST.get(),
                            ModBlocks.GENERIC_CATALYST_1.get(),
                            ModBlocks.GENERIC_CATALYST_2.get(),
                            ModBlocks.GENERIC_CATALYST_3.get(),
                            ModBlocks.GENERIC_CATALYST_4.get(),
                            ModBlocks.GENERIC_CATALYST_5.get(),
                            ModBlocks.GENERIC_CATALYST_6.get(),
                            ModBlocks.GENERIC_CATALYST_7.get(),
                            ModBlocks.GENERIC_CATALYST_8.get(),
                            ModBlocks.GENERIC_CATALYST_9.get(),
                            ModBlocks.GENERIC_CATALYST_10.get(),
                            ModBlocks.GENERIC_CATALYST_11.get(),
                            ModBlocks.GENERIC_CATALYST_12.get()
                    ).build(null));

    public static void register(IEventBus eventBus) {
        BLOCK_ENTITY_TYPES.register(eventBus);
    }
}
