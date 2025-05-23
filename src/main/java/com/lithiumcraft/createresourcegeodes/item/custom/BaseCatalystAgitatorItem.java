package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;
import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.sound.ModSounds;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import com.lithiumcraft.createresourcegeodes.util.CatalystShapeTasks;
import com.mojang.logging.LogUtils;
import net.minecraft.ChatFormatting;
import net.minecraft.client.gui.screens.Screen;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.network.chat.Component;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvent;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.TooltipFlag;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.entity.BlockEntity;
import org.slf4j.Logger;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.item.Item;
import net.minecraft.world.item.context.UseOnContext;
import net.minecraft.world.level.Level;

import javax.annotation.Nullable;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.UUID;

public abstract class BaseCatalystAgitatorItem extends Item {
    private static final Logger LOGGER = LogUtils.getLogger();
    protected static final Map<BlockPos, Long> lastUseTimestamps = new HashMap<>();
    private static final Map<UUID, Map<BlockPos, Integer>> failureCounts = new HashMap<>();


    public BaseCatalystAgitatorItem(Properties properties) {
        super(properties);
    }

    public abstract CatalystAgitatorTier getTier();

    @Override
    public void appendHoverText(ItemStack stack, TooltipContext context, List<Component> tooltipComponents, TooltipFlag tooltipFlag) {
        if (Screen.hasShiftDown()) {
            tooltipComponents.add(Component.translatable("tooltip.createresourcegeodes.catalyst_agitator.tooltip"));
        } else {
            CatalystAgitatorTier tier = this.getTier();
            tooltipComponents.add(Component.literal("Agitator Tier: " + tier.getLevel())
                    .withStyle(ChatFormatting.DARK_PURPLE));
        }
    }


    @Override
    public InteractionResult useOn(UseOnContext context) {
        ItemStack item = context.getItemInHand();
        Level level = context.getLevel();
        Player player = context.getPlayer();
        BlockPos pos = context.getClickedPos();

        if (level.isClientSide) return InteractionResult.SUCCESS;

        BlockEntity be = level.getBlockEntity(pos);
        if (!(be instanceof CatalystBlockEntity catalystBE)) return InteractionResult.SUCCESS;

        ServerLevel serverLevel = (ServerLevel) level;
        CatalystGeneratorDefinition def = catalystBE.getGeneratorDefinition();
        if (def == null) return InteractionResult.FAIL;

        // Custom agitator check
        Item customAgitator = def.customAgitatorItem();
        ResourceLocation heldItemId = BuiltInRegistries.ITEM.getKey(item.getItem());
        ResourceLocation customItemId = (customAgitator != null) ? BuiltInRegistries.ITEM.getKey(customAgitator) : null;
        boolean isMatchingCustom = customItemId != null && heldItemId.equals(customItemId);

        // Cooldown check
        long gameTime = serverLevel.getGameTime();
        long lastUsed = lastUseTimestamps.getOrDefault(pos, -1L);
        int cooldown = def.cooldownTicks();
        if (gameTime - lastUsed < cooldown) {
            serverLevel.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.NOTE_BLOCK_BASS, SoundSource.BLOCKS, 1.0f, 1.0f);
            serverLevel.sendParticles(ParticleTypes.SMOKE,
                    pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                    8, 0.2, 0.2, 0.2, 0.01);
            return InteractionResult.FAIL;
        }

        // Validation: tier OR item
        if (customItemId != null) {
            // Custom item required
            if (!isMatchingCustom) {
                return handleFailureFeedback(serverLevel, pos, player) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        } else {
            // Tier-based check
            CatalystAgitatorTier agitatorTier = getTier();
            int requiredTier = def.minimumTier();

            if (agitatorTier.getLevel() < requiredTier) {
                return handleFailureFeedback(serverLevel, pos, player) ? InteractionResult.SUCCESS : InteractionResult.FAIL;
            }
        }

        // ✅ Passed all checks — activate
        lastUseTimestamps.put(pos, gameTime);
        catalystBE.resetCooldown();

        Block generator = def.generatorBlock();
        CatalystShape shape = def.shape();
        int radius = def.radius();
        float fill = def.fillPercentage();

        if (!player.getAbilities().instabuild) {
            item.shrink(1);
        }

        switch (shape) {
            case SPHERE -> CatalystShapeTasks.queueSphere(level, pos, generator, radius, fill);
            case CUBE -> CatalystShapeTasks.queueCube(level, pos, generator, radius, fill);
        }

        return InteractionResult.SUCCESS;
    }



    private boolean handleFailureFeedback(ServerLevel level, BlockPos pos, @Nullable Player player) {
        UUID playerId = player != null ? player.getUUID() : UUID.randomUUID();
        Map<BlockPos, Integer> blockFailures = failureCounts.computeIfAbsent(playerId, k -> new HashMap<>());
        int fails = blockFailures.getOrDefault(pos, 0);

        if (fails < 10) {
            SoundEvent sound = (fails == 9)
                    ? ModSounds.DONT_BE_A_BOT.get()
                    : ModSounds.AGITATOR_INVALID_TIER.get();

            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(), sound, SoundSource.BLOCKS, 1f, 1f);
            level.sendParticles(ParticleTypes.SMOKE,
                    pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                    8, 0.2, 0.2, 0.2, 0.01);

            blockFailures.put(pos, fails + 1);
        } else {
            Map<BlockPos, Integer> existing = failureCounts.get(playerId);
            if (existing != null) {
                existing.remove(pos);
            }
        }

        return false;
    }

}