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

package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.Config;
import com.lithiumcraft.createresourcegeodes.block.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import com.lithiumcraft.createresourcegeodes.block.entity.ModBlockEntities;
import com.lithiumcraft.createresourcegeodes.config.WandMode;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import com.lithiumcraft.createresourcegeodes.util.WandModeUtil;
import net.minecraft.ChatFormatting;
import net.minecraft.core.component.DataComponents;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.network.chat.Component;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.Containers;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResultHolder;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.*;
import net.minecraft.world.item.component.CustomData;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.List;
import java.util.Random;

public class ActivatorWandItem extends Item {

    public static final Random RAND = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivatorWandItem.class);

    public ActivatorWandItem(Properties properties) {
        super(new Item.Properties().stacksTo(1).durability(65).rarity(Rarity.RARE));
    }

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltip, TooltipFlag flag) {
        tooltip.add(Component.literal("Mode: " + WandModeUtil.getMode(stack)).withStyle(ChatFormatting.DARK_PURPLE));
    }

    @Override
    public boolean isEnchantable(ItemStack stack) {
        return Config.catalystWandDurability; // allows enchanting at a table
    }

    @Override
    public int getEnchantmentValue() {
        return Config.catalystWandDurability ? 15 : 0;// controls enchantment quality
    }

    @Override
    public boolean isBookEnchantable(ItemStack stack, ItemStack book) {
        return Config.catalystWandDurability;// allows use with enchanted books in an anvil
    }

    @Override
    public boolean isValidRepairItem(ItemStack stack, ItemStack repairCandidate) {
        return repairCandidate.is(Items.NETHERITE_INGOT);
    }

    @Override
    public boolean hasCraftingRemainingItem(ItemStack stack) {
        return true;
    }

    @Override
    public ItemStack getCraftingRemainingItem(ItemStack stack) {
        stack.setDamageValue(stack.getDamageValue() + 1);
        if (stack.getDamageValue() >= stack.getMaxDamage()) stack.setCount(0);
        return stack.copy();
    }

    @Override
    public InteractionResult useOn(UseOnContext context) {
        Level level = context.getLevel();
        BlockPos pos = context.getClickedPos();
        BlockState state = level.getBlockState(pos);
        Block block = state.getBlock();

        if (!(block instanceof CatalystDataProvider)) return InteractionResult.PASS;

        if (level.isClientSide()) return InteractionResult.SUCCESS;

        WandMode mode = WandModeUtil.getMode(context.getItemInHand());

        if (mode == WandMode.MOVE) {
            return tryMoveCatalyst(context, level, pos, state);
        } else if (mode == WandMode.BREAK) {
            return tryBreakCatalyst(context, level, pos, state);
        }

        return InteractionResult.PASS;
    }

    @Override
    public InteractionResultHolder<ItemStack> use(Level level, Player player, InteractionHand hand) {
        ItemStack stack = player.getItemInHand(hand);

        if (player.isShiftKeyDown()) {
            if (!level.isClientSide) {
                WandMode currentMode = WandModeUtil.getMode(stack);
                WandMode nextMode = currentMode.next();
                WandModeUtil.setMode(stack, nextMode);
                player.displayClientMessage(Component.literal("Switched to " + nextMode.name() + " Mode"), true);
            }
            return InteractionResultHolder.success(stack);
        }

        return InteractionResultHolder.pass(stack);
    }

    private InteractionResult tryMoveCatalyst(UseOnContext context, Level level, BlockPos oldPos, BlockState oldState) {
        int x = oldPos.getX();
        int y = oldPos.getY();
        int z = oldPos.getZ();
        int offset = Config.moveCatalystDistance;

        switch (context.getClickedFace().getOpposite()) {
            case UP -> y += offset;
            case DOWN -> y -= offset;
            case EAST -> x += offset;
            case WEST -> x -= offset;
            case NORTH -> z -= offset;
            case SOUTH -> z += offset;
        }

        BlockPos newPos = new BlockPos(x, y, z);

        boolean validTarget = (
                (Config.catalystMoveIgnoreWater && level.getBlockState(newPos).is(Blocks.WATER)) ||
                        level.getBlockState(newPos).isAir()
        );

        if (!validTarget) return InteractionResult.FAIL;

        if (y < level.getMinBuildHeight() + 10 || y > level.getMaxBuildHeight() - 10) return InteractionResult.FAIL;

        // Get old BE and its NBT
        BlockEntity oldBE = level.getBlockEntity(oldPos);
        CompoundTag preservedTag = null;
        if (oldBE instanceof CatalystBlockEntity oldCatalyst) {
            preservedTag = oldCatalyst.saveCustomData(level.registryAccess());
        }

        level.setBlockAndUpdate(oldPos, Blocks.AIR.defaultBlockState());
        level.setBlockAndUpdate(newPos, oldState.getBlock().defaultBlockState());

        BlockEntity newBE = level.getBlockEntity(newPos);
        if (newBE instanceof CatalystBlockEntity newCatalyst && preservedTag != null) {
            newCatalyst.loadCustomData(preservedTag, level.registryAccess());
        }

        // Optional: sound/particles
        level.playSound(null, oldPos, ModSounds.CATALYST_BLOCK_TELEPORT.get(), SoundSource.BLOCKS, 1f, 1f);
        for (int i = 0; i < 2; ++i) {
            level.addParticle(ParticleTypes.PORTAL, oldPos.getX(), oldPos.getY(), oldPos.getZ(),
                    (RAND.nextDouble() - 0.5D) * 2.0D, -RAND.nextDouble(), (RAND.nextDouble() - 0.5D) * 2.0D);
        }

        if (Config.catalystWandDurability) {
            context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
        }

        context.getPlayer().getCooldowns().addCooldown(this, 20);
        return InteractionResult.SUCCESS;
    }


    private InteractionResult tryBreakCatalyst(UseOnContext context, Level level, BlockPos pos, BlockState state) {
        BlockEntity be = level.getBlockEntity(pos);

        if (be instanceof CatalystBlockEntity catalyst && catalyst.isUserPlaced()) {
            CompoundTag tag = catalyst.saveCustomData(level.registryAccess());
            ItemStack dropped = new ItemStack(state.getBlock().asItem());

            dropped.set(DataComponents.BLOCK_ENTITY_DATA, CustomData.of(tag));

            Containers.dropItemStack(level, pos.getX(), pos.getY(), pos.getZ(), dropped);
            level.removeBlock(pos, false);

            level.playSound(null, pos, SoundEvents.AMETHYST_BLOCK_BREAK, SoundSource.BLOCKS, 1f, 1f);

            if (Config.catalystWandDurability) {
                context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
            }

            context.getPlayer().getCooldowns().addCooldown(this, 10);
            return InteractionResult.SUCCESS;
        }

        return InteractionResult.FAIL;
    }
}
