package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.registries.Registries;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.CreativeModeTab;
import net.minecraft.world.item.ItemStack;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;


public class ModCreativeModeTabs {
    public static final DeferredRegister<CreativeModeTab> CREATIVE_MODE_TAB =
            DeferredRegister.create(Registries.CREATIVE_MODE_TAB, CreateResourceGeodes.MOD_ID);

    public static final Supplier<CreativeModeTab> GEODE_ITEM_TABS = CREATIVE_MODE_TAB.register("createresourcegeodes_tab",
            () -> CreativeModeTab.builder().icon(() -> new ItemStack(ModItems.ACTIVATOR_WAND.get()))
                    .title(Component.translatable("creativetab.createresourcegeodes_tab"))
                    .displayItems((itemDisplayParameters, output) -> {
                        output.accept(ModBlocks.ASURINE_CATALYST);
                        output.accept(ModBlocks.CRIMSITE_CATALYST);
                        output.accept(ModBlocks.OCHRUM_CATALYST);
                        output.accept(ModBlocks.VERIDIUM_CATALYST);
                        output.accept(ModBlocks.SKY_STONE_CATALYST);
                        output.accept(ModItems.ACTIVATOR_WAND);
                        output.accept(ModItems.CATALYST_AGITATOR);
                        output.accept(ModItems.CATALYST_CORE);
                    }).build());


    public static void register(IEventBus eventBus) {
        CREATIVE_MODE_TAB.register(eventBus);
    }
}
