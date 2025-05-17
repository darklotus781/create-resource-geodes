package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.Config;
import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.util.CatalystShapeTasks;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import com.mojang.logging.LogUtils;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import org.slf4j.Logger;

import java.util.HashMap;
import java.util.Map;
import java.util.Random;
import java.util.Hashtable;

@Deprecated
public class CatalystAgitatorItem extends Item {

    private static final Logger LOGGER = LogUtils.getLogger();
    public static final Hashtable<BlockPos, Tuple<Integer, LevelAccessor>> scheduled = new Hashtable<>();
    public static final Map<BlockPos, Long> lastUseTimestamps = new HashMap<>();

    public CatalystAgitatorItem(Properties properties) {
        super(properties);
    }

    public InteractionResult useOn(UseOnContext context) {
        ItemStack item = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        Block clickedBlock = level.getBlockState(context.getClickedPos()).getBlock();
        BlockPos positionClicked = context.getClickedPos();

        if (level.isClientSide) return InteractionResult.SUCCESS; // Only run on server

        // Ensure the block clicked is a CatalystBlock
        if (clickedBlock instanceof CatalystDataProvider provider) {
            ServerLevel serverLevel = (ServerLevel) level;
            Block generator = provider.getGeneratorBlock(serverLevel);
            int radius = provider.getRadius(serverLevel);
            float fill = provider.getFillPercentage(serverLevel);
            CatalystShape shape = provider.getShape(serverLevel);
            long currentTime = serverLevel.getGameTime();
            int cooldown = provider.getCooldown(serverLevel);
            long lastUsed = CatalystAgitatorItem.lastUseTimestamps.getOrDefault(positionClicked, -1L);

            if (currentTime - lastUsed < cooldown) {
//                LOGGER.info("Catalyst at {} is still on cooldown ({} ticks remaining)", positionClicked, cooldown - (currentTime - lastUsed));
                serverLevel.sendParticles(ParticleTypes.SMOKE, positionClicked.getX() + 0.5, positionClicked.getY() + 1.2, positionClicked.getZ() + 0.5, 8, 0.2, 0.2, 0.2, 0.01);
                return InteractionResult.FAIL;
            }

            // Record new timestamp
            CatalystAgitatorItem.lastUseTimestamps.put(positionClicked, currentTime);

            if (generator == null) {
                LOGGER.warn("No generator block found for catalyst {} at {}", provider.getCatalystId(), positionClicked);
                return InteractionResult.FAIL;
            }

            if (!player.getAbilities().instabuild) {
                item.shrink(1);
//                player.getCooldowns().addCooldown(this, 100);
            }

            // Queue the sphere to be generated in the next tick
            switch (shape) {
                case SPHERE -> CatalystShapeTasks.queueSphere(level, positionClicked, generator, radius, fill);
                case CUBE -> CatalystShapeTasks.queueCube(level, positionClicked, generator, radius, fill);
            }

            return InteractionResult.SUCCESS;

        }

        return InteractionResult.SUCCESS;
    }
}
