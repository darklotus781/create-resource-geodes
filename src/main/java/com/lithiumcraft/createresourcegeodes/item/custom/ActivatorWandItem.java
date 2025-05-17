package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.Config;
import com.lithiumcraft.createresourcegeodes.block.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.EquipmentSlot;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.Rarity;
import net.minecraft.world.item.component.Tool;
import net.minecraft.world.item.enchantment.Enchantment;
import net.minecraft.world.item.enchantment.Enchantments;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;

import java.util.Random;

public class ActivatorWandItem extends Item {

    public static final Random RAND = new Random();
    private static final Logger LOGGER = LoggerFactory.getLogger(ActivatorWandItem.class);

    public ActivatorWandItem(Properties properties) {
        super(new Item.Properties().stacksTo(1).durability(65).rarity(Rarity.RARE));
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
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        BlockPos positionClicked = context.getClickedPos();
        BlockState state = context.getLevel().getBlockState(positionClicked);

        // Ensure the block clicked is a CatalystBlock
        if (clickedBlock instanceof CatalystDataProvider && !level.isClientSide()) {
            int x = positionClicked.getX();
            int y = positionClicked.getY();
            int z = positionClicked.getZ();
            int iVal = Config.moveCatalystDistance;

            // Adjust position based on the clicked face
            switch (context.getClickedFace().getOpposite().getName()) {
                case "up":
                    y = y + iVal;
                    break;
                case "down":
                    y = y - iVal;
                    break;
                case "east":
                    x = x + iVal;
                    break;
                case "west":
                    x = x - iVal;
                    break;
                case "north":
                    z = z - iVal;
                    break;
                case "south":
                    z = z + iVal;
                    break;
            }

            BlockPos newPos = new BlockPos(x, y, z);

            // Check if we can move the block to the new position (water or air)
            if (((Config.catalystMoveIgnoreWater && level.getBlockState(newPos).is(Blocks.WATER)) || level.getBlockState(newPos).isAir())
                    && y <= level.getMaxBuildHeight() - 10 && y >= level.getMinBuildHeight() + 10) {
                // Remove the block at the clicked position and place it at the new position
                level.setBlockAndUpdate(positionClicked, Blocks.AIR.defaultBlockState());
                level.setBlockAndUpdate(newPos, state.getBlock().defaultBlockState());

                // Play the sound and particles at the original block position
                level.playSound(null, positionClicked, ModSounds.CATALYST_BLOCK_TELEPORT.get(), SoundSource.BLOCKS, 1f, 1f);
                for (int i = 0; i < 2; ++i) {
                    level.addParticle(ParticleTypes.PORTAL, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(),
                            (RAND.nextDouble() - 0.5D) * 2.0D, -RAND.nextDouble(), (RAND.nextDouble() - 0.5D) * 2.0D);
                }


                if (Config.catalystWandDurability) {

                    // This will apply durability damage AND respect Unbreaking enchantments
                    context.getItemInHand().hurtAndBreak(1, context.getPlayer(), EquipmentSlot.MAINHAND);
                }

                context.getPlayer().getCooldowns().addCooldown(this, 20);
            }
        }

        return InteractionResult.SUCCESS;
    }
}
