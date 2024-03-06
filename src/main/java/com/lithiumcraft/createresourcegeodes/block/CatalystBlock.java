package com.lithiumcraft.createresourcegeodes.block;

import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

abstract public class CatalystBlock extends Block {

    protected static final Logger LOGGER = LogUtils.getLogger();
    public static final int TICKS = 120; // time it takes at minimum
    protected static final String AIR_BLOCK = "minecraft:air";

    public CatalystBlock(Properties pProperties) {
        super(pProperties);
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.sendParticles(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.5, pos.getZ() + 0.5, 15, 0, 0, 0, 0.20);
    }
}

/* FILL AS A SPHERE */
//        int sphereRadius = Config.asurineCatalystRadius;
//        int centerX = pos.getX(); // Center point x-coordinate
//        int centerY = pos.getY(); // Center point y-coordinate
//        int centerZ = pos.getZ(); // Center point z-coordinate
//        double fillPercentage = Config.catalystFillPercentage; // Change this to your desired fill percentage
//
//        Random random = new Random();
//
//        for (int i = centerX - sphereRadius; i <= centerX + sphereRadius; i++) {
//            for (int j = centerY - sphereRadius; j <= centerY + sphereRadius; j++) {
//                for (int k = centerZ - sphereRadius; k <= centerZ + sphereRadius; k++) {
//                    // Calculate the distance from the center point
//                    double distance = Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(j - centerY, 2) + Math.pow(k - centerZ, 2));
//
//                    // Check if the block is within the sphere
//                    if (distance <= sphereRadius) {
//                        // Generate a random value between 0 and 1
//                        double randomValue = random.nextDouble();
//
//                        // (i, j, k) are the coordinates of filled blocks
//                        var newBlock = new BlockPos(i, j, k);
//
//                        // Check if the random value is less than the fill percentage
//                        if (randomValue < fillPercentage) {
//                            // Ensure the new block coordinate is an air block.
//                            if (!level.getBlockState(newBlock).toString().contains(AIR_BLOCK)) {
//                                continue;
//                            }
//
//                            schedulePlacement(newBlock, level);
//                        }
//                    }
//                }
//            }

/* FILL AS A CUBE */
//        int cubeSize = Config.asurineCatalystDiameter;
//        int centerX = pos.getX();
//        int centerY = pos.getY();
//        int centerZ = pos.getZ();
//        double fillPercentage = Config.catalystFillPercentage;
//
//        for (int distance = 0; distance <= cubeSize / 2; distance++) {
//            for (int i = centerX - distance; i <= centerX + distance; i++) {
//                for (int j = centerY - distance; j <= centerY + distance; j++) {
//                    for (int k = centerZ - distance; k <= centerZ + distance; k++) {
//                        // Skip generating random values for the catalyst block
//                        if (i == centerX && j == centerY && k == centerZ) {
//                            continue;
//                        }
//
//                        // Generate a random value between 0 and 1
//                        double randomValue = RAND.nextDouble();
//                        // (i, j, k) are the coordinates of filled blocks
//                        var newBlock = new BlockPos(i, j, k);
//
//                        // Check if the random value is less than the fill percentage
//                        if (randomValue <= fillPercentage) {
//                            // Ensure the new block coordinate is an air block.
//                            if (!level.getBlockState(newBlock).toString().contains(AIR_BLOCK)) {
//                                continue;
//                            }
//
//                            schedulePlacement(newBlock, level);
//                        }
//                    }
//                }
//            }
//        }