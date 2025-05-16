package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;

import java.util.*;

public class CatalystShapeTasks {
    private static final List<BlockPlacerTask> ACTIVE_TASKS = new ArrayList<>();

    public static void queueSphere(Level level, BlockPos center, Block block, int radius, float fill) {
        ACTIVE_TASKS.add(new SpherePlacer(level, center, block, radius, fill));
    }

    public static void queueCube(Level level, BlockPos center, Block block, int radius, float fill) {
        ACTIVE_TASKS.add(new CubePlacer(level, center, block, radius, fill));
    }

    public static void tick(MinecraftServer server) {
        Iterator<BlockPlacerTask> iterator = ACTIVE_TASKS.iterator();
        while (iterator.hasNext()) {
            BlockPlacerTask task = iterator.next();
            task.tick();
            if (task.isDone()) {
                iterator.remove();
            }
        }
    }

    private static class CubePlacer implements BlockPlacerTask {
        private final ServerLevel level;
        private final Queue<BlockPos> positions = new LinkedList<>();
        private final Block block;
        private static final Random RAND = new Random();
        private int delayTicks = 40; // 2 seconds at 20 TPS

        public CubePlacer(Level level, BlockPos center, Block block, int radius, float fillPercentage) {
            this.level = (ServerLevel) level;
            this.block = block;

            List<BlockPos> tempPositions = new ArrayList<>();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (RAND.nextDouble() <= fillPercentage) {
                            pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                            tempPositions.add(pos.immutable());
                        }
                    }
                }
            }

            Collections.shuffle(tempPositions, RAND);
            positions.addAll(tempPositions);
        }

        public void tick() {
            if (delayTicks-- > 0) return; // Delay not finished

            for (int i = 0; i < Config.catalystBlocksPerTick && !positions.isEmpty(); i++) {
                BlockPos pos = positions.poll();
                if (level.isInWorldBounds(pos) && level.getBlockState(pos).isAir()) {
                    level.setBlock(pos, block.defaultBlockState(), 3);
                }
            }
        }

        public boolean isDone() {
            return delayTicks <= 0 && positions.isEmpty();
        }
    }

    private static class SpherePlacer implements BlockPlacerTask {
        private final ServerLevel level;
        private final Queue<BlockPos> positions = new LinkedList<>();
        private final Block block;
        private static final Random RAND = new Random();
        private int delayTicks = 40; // 2 seconds at 20 TPS

        public SpherePlacer(Level level, BlockPos center, Block block, int radius, float fillPercentage) {
            this.level = (ServerLevel) level;
            this.block = block;

            List<BlockPos> tempPositions = new ArrayList<>();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (x * x + y * y + z * z <= radius * radius) {
                            if (RAND.nextDouble() <= fillPercentage) {
                                pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                                tempPositions.add(pos.immutable());
                            }
                        }
                    }
                }
            }

            // Shuffle for random placement order
            Collections.shuffle(tempPositions, RAND);
            positions.addAll(tempPositions);
        }

        public void tick() {
            if (delayTicks-- > 0) return; // Delay not finished

            for (int i = 0; i < Config.catalystBlocksPerTick && !positions.isEmpty(); i++) {
                BlockPos pos = positions.poll();
                if (level.isInWorldBounds(pos) && level.getBlockState(pos).isAir()) {
                    level.setBlock(pos, block.defaultBlockState(), 3);
                }
            }
        }

        public boolean isDone() {
            return delayTicks <= 0 && positions.isEmpty();
        }
    }
}