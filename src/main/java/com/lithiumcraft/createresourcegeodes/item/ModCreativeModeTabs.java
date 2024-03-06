package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.RegistryObject;

public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TABS =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateResourceGeodes.MOD_ID);

    public static final RegistryObject<CreativeModeTab> CREATERESOURCEGEODES_TAB = CREATIVE_MODE_TABS.register("createresourcegeodes_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ACTIVATOR_WAND.get()))
                    .title(Component.translatable("creativetab.createresourcegeodes_tab"))
                    .displayItems((pParameters, pOutput) -> {
                        pOutput.accept(ModBlocks.ASURINE_CATALYST.get());
                        pOutput.accept(ModBlocks.CRIMSITE_CATALYST.get());
                        pOutput.accept(ModBlocks.OCHRUM_CATALYST.get());
                        pOutput.accept(ModBlocks.VERIDIUM_CATALYST.get());
                        pOutput.accept(ModBlocks.SKY_STONE_CATALYST.get());
                        pOutput.accept(ModBlocks.FAUX_ASURINE.get());
                        pOutput.accept(ModBlocks.FAUX_CRIMSITE.get());
                        pOutput.accept(ModBlocks.FAUX_OCHRUM.get());
                        pOutput.accept(ModBlocks.FAUX_VERIDIUM.get());
                        pOutput.accept(ModBlocks.FAUX_SKY_STONE.get());
                        pOutput.accept(ModItems.ACTIVATOR_WAND.get());
                    })
                    .build());

    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TABS.register(eventBus);
    }
}
