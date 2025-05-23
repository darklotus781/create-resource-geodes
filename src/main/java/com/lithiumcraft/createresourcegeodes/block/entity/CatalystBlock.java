package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.item.custom.ActivatorWandItem;
import com.lithiumcraft.createresourcegeodes.item.custom.BaseCatalystAgitatorItem;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.neoforged.fml.ModList;

import javax.annotation.Nullable;


public abstract class CatalystBlock extends Block implements EntityBlock, CatalystDataProvider {
    public CatalystBlock(Properties properties) {
        super(properties);
    }

    private static final boolean MEKANISM_LOADED = ModList.get().isLoaded("mekanism");
    private static final boolean JDT_LOADED = ModList.get().isLoaded("justdirethings");

    @Override
    public BlockEntity newBlockEntity(BlockPos pos, BlockState state) {
        return new CatalystBlockEntity(pos, state);
    }

    @Override
    public void onPlace(BlockState state, Level level, BlockPos pos, BlockState oldState, boolean isMoving) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CatalystBlockEntity catalystBE) {
                ResourceLocation id = getCatalystId();
//                System.out.println("[CatalystBlock] onPlace() setting catalyst ID to: " + id);
                catalystBE.setCatalystId(id, (ServerLevel) level);
                catalystBE.setChanged();
                // force client update
                level.sendBlockUpdated(pos, state, state, 3);
            } else {
//                System.out.println("[CatalystBlock] BlockEntity is not a CatalystBlockEntity: " + be);
            }
        }
    }

    @Override
    public ItemInteractionResult useItemOn(ItemStack stack, BlockState state, Level level, BlockPos pos,
                                           Player player, InteractionHand hand, BlockHitResult hit) {
        if (!(level.getBlockEntity(pos) instanceof CatalystBlockEntity catalyst)) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        // ✅ Allow the activator wand to continue its own logic
        if (stack.getItem() instanceof ActivatorWandItem) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        if (stack.getItem() instanceof BaseCatalystAgitatorItem) {
            return ItemInteractionResult.PASS_TO_DEFAULT_BLOCK_INTERACTION;
        }

        // 🔜 Custom agitator logic (redstone, blaze powder, etc.)
        CatalystGeneratorDefinition def = catalyst.getGeneratorDefinition();
        if (def == null || def.customAgitatorItem() == null) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        // Must match defined custom agitator item
        if (!stack.is(def.customAgitatorItem())) {
            return ItemInteractionResult.SKIP_DEFAULT_BLOCK_INTERACTION;
        }

        // Cooldown check
        if (catalyst.isOnCooldown()) {
            level.playSound(null, pos.getX(), pos.getY(), pos.getZ(),
                    SoundEvents.NOTE_BLOCK_BASS, SoundSource.BLOCKS, 1f, 1f);
            if (level instanceof ServerLevel server) {
                server.sendParticles(ParticleTypes.SMOKE,
                        pos.getX() + 0.5, pos.getY() + 1.2, pos.getZ() + 0.5,
                        8, 0.2, 0.2, 0.2, 0.01);
            }
            return ItemInteractionResult.SUCCESS;
        }

        // Activate catalyst
        boolean success = catalyst.tryActivate(level, stack, player);
        return success ? ItemInteractionResult.SUCCESS : ItemInteractionResult.FAIL;
    }

    @Override
    public void setPlacedBy(Level level, BlockPos pos, BlockState state, @Nullable LivingEntity placer, ItemStack stack) {
        if (!level.isClientSide) {
            BlockEntity be = level.getBlockEntity(pos);
            if (be instanceof CatalystBlockEntity catalyst) {
                var data = stack.get(DataComponents.BLOCK_ENTITY_DATA);
                if (data != null) {
                    CompoundTag tag = data.copyTag();
                    catalyst.loadCustomData(tag, level.registryAccess());
                } else if (placer instanceof Player) {
                    catalyst.setUserPlaced(true);
                }
            }
        }

        super.setPlacedBy(level, pos, state, placer, stack);
    }

    private boolean isUserPlaced(LevelReader level, BlockPos pos) {
        BlockEntity be = level.getBlockEntity(pos);
        return be instanceof CatalystBlockEntity catalyst && catalyst.isUserPlaced();
    }

    @Override
    public void randomTick(BlockState state, ServerLevel level, BlockPos pos, RandomSource random) {
        level.sendParticles(ParticleTypes.PORTAL, pos.getX() + 0.5, pos.getY() + 0.5,
                pos.getZ() + 0.5, 15, 0, 0, 0, 0.20);
    }

    @Override
    public <T extends BlockEntity> BlockEntityTicker<T> getTicker(Level level, BlockState state, BlockEntityType<T> type) {
        return level.isClientSide ? null : (lvl, pos, st, be) -> {
            if (be instanceof CatalystBlockEntity catalystBE) {
                catalystBE.tickServer((ServerLevel) lvl, pos, st);
            }
        };
    }
}

