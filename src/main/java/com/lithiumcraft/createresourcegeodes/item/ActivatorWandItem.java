package com.lithiumcraft.createresourcegeodes.item;

import com.lithiumcraft.createresourcegeodes.Config;
import com.lithiumcraft.createresourcegeodes.block.CatalystBlock;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.Random;

public class ActivatorWandItem extends Item {

    public static final Random RAND = new Random();
    private static final Logger LOGGER = LogUtils.getLogger();

    public ActivatorWandItem(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public InteractionResult useOn(UseOnContext pContext) {
        BlockPos positionClicked = pContext.getClickedPos();
        BlockState state = pContext.getLevel().getBlockState(positionClicked);
        Level level = pContext.getLevel();

        if (state.getBlock() instanceof CatalystBlock) {
            int x = positionClicked.getX();
            int y = positionClicked.getY();
            int z = positionClicked.getZ();
            int iVal = Config.moveCatalystDistance;
            switch (pContext.getClickedFace().getOpposite().getName().toString()) {
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

            // If new block is water and config allows moving catalyst into water, or If new block is air and coords within build height, do it!
            if (((Config.catalystMoveIgnoreWater && level.getBlockState(newPos).is(Blocks.WATER)) || level.getBlockState(newPos).isAir()) && y <= level.getMaxBuildHeight() - 10 && y >= level.getMinBuildHeight() + 10) {
                if (!level.isClientSide()) {
                    level.setBlock(positionClicked, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS, 1);
                    level.setBlock(newPos, state.getBlock().defaultBlockState(), Block.UPDATE_CLIENTS, 1);

                    level.playSeededSound(null, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(),
                            ModSounds.CATALYST_BLOCK_TELEPORT.get(), SoundSource.BLOCKS, 1f, 1f, 0);

                    for (int i = 0; i < 2; ++i) {
                        level.addParticle(ParticleTypes.PORTAL, positionClicked.getX(), positionClicked.getY(), positionClicked.getZ(), (RAND.nextDouble() - 0.5D) * 2.0D, -RAND.nextDouble(), (RAND.nextDouble() - 0.5D) * 2.0D);
                    }
                }

                pContext.getItemInHand().hurtAndBreak(1, pContext.getPlayer(),
                        player -> player.broadcastBreakEvent(player.getUsedItemHand()));
            }
        }

        return InteractionResult.SUCCESS;
    }
}