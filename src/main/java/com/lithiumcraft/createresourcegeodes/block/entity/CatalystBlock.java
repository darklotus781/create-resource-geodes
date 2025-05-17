package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.util.RandomSource;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.EntityBlock;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.entity.BlockEntityTicker;
import net.minecraft.world.level.block.entity.BlockEntityType;
import net.minecraft.world.level.block.state.BlockState;


public abstract class CatalystBlock extends Block implements EntityBlock, CatalystDataProvider {
    public CatalystBlock(Properties properties) {
        super(properties);
    }

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

