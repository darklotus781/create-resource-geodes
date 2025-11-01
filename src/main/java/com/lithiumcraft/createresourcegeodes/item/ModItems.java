/*
 * create-resource-geodes
 * Copyright (c) 2025 DarkLotus (DarkLotus781) / LithiumCraft
 *
 * This program is free software: you can redistribute it and/or modify
 * it under the terms of the GNU General Public License as published by
 * the Free Software Foundation, either version 3 of the License, or
 * (at your option) any later version.
 *
 * This program is distributed in the hope that it will be useful,
 * but WITHOUT ANY WARRANTY; without even the implied warranty of
 * MERCHANTABILITY or FITNESS FOR A PARTICULAR PURPOSE.  See the
 * GNU General Public License for more details.
 *
 * You should have received a copy of the GNU General Public License
 * along with this program.  If not, see <http://www.gnu.org/licenses/>.
 */

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
