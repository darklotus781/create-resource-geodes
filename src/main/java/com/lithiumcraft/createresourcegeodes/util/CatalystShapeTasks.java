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
//        System.out.println("[queueSphere] Skipping Placement");
        ACTIVE_TASKS.add(new SpherePlacer(level, center, block, radius, fill));
    }

    public static void queueCube(Level level, BlockPos center, Block block, int radius, float fill) {
//        System.out.println("[queueCube] Skipping Placement");
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

            List<BlockPos> allPositions = new ArrayList<>();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                        allPositions.add(pos.immutable());
                    }
                }
            }

            Collections.shuffle(allPositions, RAND);
            int count = Math.round(allPositions.size() * fillPercentage);
            positions.addAll(allPositions.subList(0, count));
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

            List<BlockPos> allPositions = new ArrayList<>();
            BlockPos.MutableBlockPos pos = new BlockPos.MutableBlockPos();

            for (int x = -radius; x <= radius; x++) {
                for (int y = -radius; y <= radius; y++) {
                    for (int z = -radius; z <= radius; z++) {
                        if (x * x + y * y + z * z <= radius * radius) {
                            pos.set(center.getX() + x, center.getY() + y, center.getZ() + z);
                            allPositions.add(pos.immutable());
                        }
                    }
                }
            }

            Collections.shuffle(allPositions, RAND);
            int count = Math.round(allPositions.size() * fillPercentage);
            positions.addAll(allPositions.subList(0, count));
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