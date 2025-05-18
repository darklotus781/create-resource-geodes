// CatalystBlockEntity.java
package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.network.ClientboundSyncCatalystDataPacket;
import com.lithiumcraft.createresourcegeodes.network.ModNetwork;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import net.minecraft.core.BlockPos;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

public class CatalystBlockEntity extends BlockEntity implements CatalystDataProvider {
    private ResourceLocation catalystId;
    private int cachedCooldown = -1;
    private int cachedTier = -1;
    private int cooldownTicksRemaining = 0;
    public int clientSyncedCooldown = 0;
    public int clientSyncedTier = 0;

    public CatalystBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CATALYST.get(), pos, state);
    }

    public void setCatalystId(ResourceLocation id) {
        this.catalystId = id;
        this.setChanged();
    }

    public void setCatalystId(ResourceLocation id, ServerLevel level) {
        this.catalystId = id;

        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(id);

        if (def != null) {
            cachedCooldown = def.cooldownTicks();
            cachedTier = def.minimumTier();
//            System.out.println("[CatalystBE] Loaded from registry: " + id);
        } else {
            cachedCooldown = getDefaultCooldown();
            cachedTier = getDefaultMinimumTier();
//            System.out.println("[CatalystBE] Using fallback for: " + id);
        }

        setChanged();

        // ✅ Sync once immediately
        ModNetwork.sendCatalystSync(
                new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier),
                level,
                worldPosition
        );
        level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
    }

    public ResourceLocation getCatalystId() {
        return catalystId;
    }

    @Override
    public Block getDefaultGeneratorBlock() {
        return Blocks.INFESTED_DEEPSLATE;
    }

//    public int getCachedCooldown() {
//        return cachedCooldown;
//    }
//
//    public int getCachedTier() {
//        return cachedTier;
//    }
//
//    public int getCooldownTicksRemaining() {
//        return cooldownTicksRemaining;
//    }

    public void tickServer(ServerLevel level, BlockPos pos, BlockState state) {
        if (catalystId == null) {
            ResourceLocation id = level.getBlockState(worldPosition).getBlock() instanceof CatalystDataProvider provider
                    ? provider.getCatalystId()
                    : null;

            if (id != null) {
                System.out.println("[CatalystBE] Repairing old catalyst at " + worldPosition);
                ensureCachedTier();
                setCatalystId(id, level);
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3); // <--- force client refresh
            }
        }


        if (cooldownTicksRemaining > 0) {
            cooldownTicksRemaining--;
            setChanged();

            // Send sync packet every second
            if (cooldownTicksRemaining % 20 == 0) {
//                System.out.println("[CatalystBE] Sending sync packet: " + cooldownTicksRemaining);

                ModNetwork.sendCatalystSync(
                        new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier),
                        level,
                        worldPosition
                );
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3); // <--- force client refresh
            }
        }
    }

    public void resetCooldown() {
//        System.out.println("[CatalystBE] Cooldown reset to " + cachedCooldown + " at " + worldPosition);

        this.cooldownTicksRemaining = cachedCooldown;
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (catalystId != null) {
            tag.putString("CatalystId", catalystId.toString());
        }

        tag.putInt("CachedCooldown", cachedCooldown);
        tag.putInt("CachedTier", cachedTier);
        tag.putInt("RemainingCooldown", cooldownTicksRemaining);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (tag.contains("CatalystId")) {
            catalystId = ResourceLocation.parse(tag.getString("CatalystId"));
        }

        cachedCooldown = tag.getInt("CachedCooldown");
        cachedTier = tag.getInt("CachedTier");
        cooldownTicksRemaining = tag.getInt("RemainingCooldown");
    }

    @Override
    public void onLoad() {
        super.onLoad();

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            if (getBlockState().getBlock() instanceof CatalystDataProvider provider) {
                cachedTier = provider.getMinimumTier(serverLevel);
                cachedCooldown = provider.getCooldown(serverLevel);
            }

            ModNetwork.sendCatalystSync(
                    new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier),
                    serverLevel,
                    worldPosition
            );
        }
    }

    private void ensureCachedTier() {
        if (cachedTier < 0 && level instanceof ServerLevel serverLevel) {
            if (getBlockState().getBlock() instanceof CatalystDataProvider provider) {
                cachedTier = provider.getMinimumTier(serverLevel);
            }
        }
    }
}
