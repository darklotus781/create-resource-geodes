package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;
import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import com.lithiumcraft.createresourcegeodes.util.CatalystShapeTasks;
import com.mojang.logging.LogUtils;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import java.util.HashMap;
import java.util.Map;

public abstract class BaseCatalystAgitatorItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected static final Map<BlockPos, Long> lastUseTimestamps = new HashMap<>();

    public BaseCatalystAgitatorItem(Properties properties) {
        super(properties);
    }

    public abstract CatalystAgitatorTier getTier();

    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack item = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();
        Block clickedBlock = level.getBlockState(pos).getBlock();

        if (level.isClientSide) return InteractionResult.SUCCESS;
        if (!(clickedBlock instanceof CatalystDataProvider provider)) return InteractionResult.SUCCESS;

        ServerLevel serverLevel = (ServerLevel) level;
        CatalystAgitatorTier agitatorTier = getTier();
        int requiredTier = provider.getMinimumTier(serverLevel);

        long gameTime = serverLevel.getGameTime();
        long lastUsed = lastUseTimestamps.getOrDefault(pos, -1L);
        int cooldown = provider.getCooldown(serverLevel);

        if (gameTime - lastUsed < cooldown) {
            serverLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.NOTE_BLOCK_BASS, SoundSource.BLOCKS, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 8, 0.2, 0.2, 0.2, 0.01);
            return InteractionResult.FAIL;
        }

        if (agitatorTier.getLevel() < requiredTier) {
            serverLevel.playSound(null, pos, ModSounds.AGITATOR_INVALID_TIER.get(), SoundSource.BLOCKS, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.SMOKE, pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5, 8, 0.2, 0.2, 0.2, 0.01);
            return InteractionResult.FAIL;
        }

        lastUseTimestamps.put(pos, gameTime);

        Block generator = provider.getGeneratorBlock(serverLevel);
        if (generator == null) {
            LOGGER.warn("No generator block found for catalyst {} at {}", provider.getCatalystId(), pos);
            return InteractionResult.FAIL;
        }

        CatalystShape shape = provider.getShape(serverLevel);
        int radius = provider.getRadius(serverLevel);
        float fill = provider.getFillPercentage(serverLevel);

        if (!player.getAbilities().instabuild) {
            item.shrink(1);
        }

        switch (shape) {
            case SPHERE -> CatalystShapeTasks.queueSphere(level, pos, generator, radius, fill);
            case CUBE -> CatalystShapeTasks.queueCube(level, pos, generator, radius, fill);
        }

        return InteractionResult.SUCCESS;
    }
}
