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

package com.lithiumcraft.createresourcegeodes.compat.jade;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import com.lithiumcraft.createresourcegeodes.item.custom.BaseCatalystAgitatorItem;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.ChatFormatting;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

import java.util.Optional;

@WailaPlugin
public class JadePlugin implements IWailaPlugin {

    @Override
    public void registerClient(IWailaClientRegistration registration) {
        registration.registerBlockComponent(CatalystTooltipProvider.INSTANCE, CatalystBlock.class);
    }
}

class CatalystTooltipProvider implements IBlockComponentProvider {
    public static final CatalystTooltipProvider INSTANCE = new CatalystTooltipProvider();

    @Override
    public void appendTooltip(ITooltip tooltip, BlockAccessor accessor, IPluginConfig config) {
        BlockEntity be = accessor.getBlockEntity();
        if (!(be instanceof CatalystBlockEntity catalystBE)) return;

        int cooldown = catalystBE.clientSyncedCooldown;
        if (cooldown <= 0) {
            tooltip.add(Component.literal("Ready to be Agitated!").withStyle(ChatFormatting.GREEN));
        } else {
            int seconds = cooldown / 20;
            tooltip.add(Component.literal("Ready in " + seconds + " seconds").withStyle(ChatFormatting.YELLOW));
        }

        Optional<ResourceLocation> customItemOpt = catalystBE.clientSyncedAgitatorItem;
        Player player = accessor.getPlayer();
        boolean showRequirement = true;

        if (customItemOpt != null && customItemOpt.isPresent()) {
            // Custom agitator item
            ResourceLocation customItem = customItemOpt.get();

            if (player != null) {
                ItemStack held = player.getMainHandItem();
                ResourceLocation heldItemId = BuiltInRegistries.ITEM.getKey(held.getItem());
                if (heldItemId.equals(customItem)) {
                    showRequirement = false;
                }
            }

            if (showRequirement) {
                Item item = BuiltInRegistries.ITEM.get(customItem);
                tooltip.add(Component.literal("Requires: ")
                        .append(Component.translatable(item.getDescriptionId()))
                        .withStyle(ChatFormatting.DARK_RED));
            }

        } else {
            // Tier fallback
            int tier = catalystBE.clientSyncedTier;
            if (tier <= 0 && accessor.getBlockState().getBlock() instanceof CatalystDataProvider provider) {
                if (accessor.getLevel().isClientSide()) {
                    tier = provider.getDefaultMinimumTier();
                } else {
                    tier = provider.getMinimumTier((ServerLevel) accessor.getLevel());
                }
            }

            if (player != null) {
                ItemStack heldItem = player.getMainHandItem();
                if (heldItem.getItem() instanceof BaseCatalystAgitatorItem agitator) {
                    if (agitator.getTier().getLevel() >= tier) {
                        showRequirement = false;
                    }
                }
            }

            if (showRequirement) {
                tooltip.add(Component.literal("Requires Tier: " + tier).withStyle(ChatFormatting.DARK_RED));
            }
        }
    }



    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "catalyst_tooltip");
    }
}
