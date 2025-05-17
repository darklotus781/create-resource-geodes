package com.lithiumcraft.createresourcegeodes.compat.jade;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import com.lithiumcraft.createresourcegeodes.item.custom.BaseCatalystAgitatorItem;
import net.minecraft.ChatFormatting;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.entity.BlockEntity;
import snownee.jade.api.*;
import snownee.jade.api.config.IPluginConfig;

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

        if (catalystBE.clientSyncedCooldown <= 0) {
            tooltip.add(Component.literal("⏳ Ready to be Agitated!").withStyle(ChatFormatting.GREEN));
        } else {
            int seconds = catalystBE.clientSyncedCooldown / 20;
            tooltip.add(Component.literal("⏳ Ready in " + seconds + " seconds").withStyle(ChatFormatting.YELLOW));
        }

        Player player = accessor.getPlayer();
        if (player != null) {
            ItemStack heldItem = player.getMainHandItem();
            if (heldItem.getItem() instanceof BaseCatalystAgitatorItem agitator) {
                if (agitator.getTier().getLevel() < catalystBE.clientSyncedTier) {
                    tooltip.add(Component.literal("🔧 Requires Tier: " + catalystBE.clientSyncedTier)
                            .withStyle(ChatFormatting.DARK_RED));
                }
            } else {
                tooltip.add(Component.literal("🔧 Requires Tier: " + catalystBE.clientSyncedTier)
                        .withStyle(ChatFormatting.DARK_RED));
            }
        }
    }

    @Override
    public ResourceLocation getUid() {
        return ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "catalyst_tooltip");
    }
}
