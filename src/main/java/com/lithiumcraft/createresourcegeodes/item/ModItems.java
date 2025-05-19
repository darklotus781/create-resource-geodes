package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.custom.*;
import net.minecraft.network.chat.Component;
import net.minecraft.world.item.*;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredItem;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.List;

public class ModItems {
    public static final DeferredRegister.Items ITEMS = DeferredRegister.createItems(CreateResourceGeodes.MOD_ID);

    public static final DeferredItem<Item> ACTIVATOR_WAND = ITEMS.register("catalyst_activator_wand",
            () -> new ActivatorWandItem(new Item.Properties()));

    public static final DeferredItem<Item> CATALYST_AGITATOR = ITEMS.register("catalyst_agitator",
            () -> new CatalystAgitatorTier1Item(new Item.Properties().rarity(Rarity.COMMON)));

    public static final DeferredItem<Item> CATALYST_AGITATOR_TIER_2 = ITEMS.register("catalyst_agitator_tier_2",
            () -> new CatalystAgitatorTier2Item(new Item.Properties().rarity(Rarity.UNCOMMON)));

    public static final DeferredItem<Item> CATALYST_AGITATOR_TIER_3 = ITEMS.register("catalyst_agitator_tier_3",
            () -> new CatalystAgitatorTier3Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> CATALYST_AGITATOR_TIER_4 = ITEMS.register("catalyst_agitator_tier_4",
            () -> new CatalystAgitatorTier4Item(new Item.Properties().rarity(Rarity.EPIC)));

    public static final DeferredItem<Item> CATALYST_CORE = ITEMS.register("catalyst_core",
            () -> new Item(new Item.Properties().stacksTo(1).rarity(Rarity.EPIC)) {
                @Override
                public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
                    tooltipComponents.add(Component.translatable("tooltip.createresourcegeodes.catalyst_core.tooltip"));
                    super.appendHoverText(stack, context, tooltipComponents, tooltipFlag);
                }
            });

    public static final DeferredItem<Item> CATALYST_ACTIVATOR_WAND_SHAFT = ITEMS.register("catalyst_activator_wand_shaft",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static final DeferredItem<Item> CATALYST_ACTIVATOR_WAND_GEM = ITEMS.register("catalyst_activator_wand_gem",
            () -> new Item(new Item.Properties().rarity(Rarity.RARE)));

    public static void register(IEventBus eventBus) {
        ITEMS.register(eventBus);
    }
}
