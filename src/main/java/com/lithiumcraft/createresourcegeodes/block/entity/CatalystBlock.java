package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.component.DataComponents;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.ItemInteractionResult;
import net.minecraft.world.entity.LivingEntity;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.Explosion;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelReader;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.level.material.PushReaction;
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
            } else {
//                System.out.println("[CatalystBlock] BlockEntity is not a CatalystBlockEntity: " + be);
            }
        }
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

//    @Override
//    public void attack(BlockState state, Level level, BlockPos pos, Player player) {
//        if (!isUserPlaced(level, pos)) {
//            level.sendBlockUpdated(pos, state, state, 3);
//        } else {
//            super.attack(state, level, pos, player);
//        }
//    }
//
//    @Override
//    public BlockState playerWillDestroy(Level level, BlockPos pos, BlockState state, Player player) {
//        return state;
//    }
//
//    @Override
//    public float getDestroyProgress(BlockState state, Player player, BlockGetter level, BlockPos pos) {
//        return 0.0F;
//    }
//
//    @Override
//    public PushReaction getPistonPushReaction(BlockState state) {
//        return PushReaction.BLOCK;
//    }
//
//    @Override
//    public float getExplosionResistance(BlockState state, BlockGetter level, BlockPos pos, Explosion explosion) {
//        return 3600000.0F;
//    }
//
//    @Override
//    public boolean canHarvestBlock(BlockState state, BlockGetter level, BlockPos pos, Player player) {
//        return false;
//    }

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

