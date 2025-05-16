package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.custom.ActivatorWandItem;
import com.lithiumcraft.createresourcegeodes.item.custom.CatalystAgitatorItem;
import net.minecraft.core.component.DataComponents;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateResourceGeodes.MOD_ID);

    public static final DeferredItem<Item> ACTIVATOR_WAND = ITEMS.register("catalyst_activator_wand",
            () -> new ActivatorWandItem(new Item.Properties()) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.createresourcegeodes.catalyst_activator_wand.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CATALYST_AGITATOR = ITEMS.register("catalyst_agitator",
            () -> new CatalystAgitatorItem(new Item.Properties().stacksTo(64)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.createresourcegeodes.catalyst_agitator.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CATALYST_CORE = ITEMS.register("catalyst_core",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.createresourcegeodes.catalyst_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
