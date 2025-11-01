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

// CatalystBlockEntity.java
package com.lithiumcraft.createresourcegeodes.block.entity;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.network.ClientboundSyncCatalystDataPacket;
import com.lithiumcraft.createresourcegeodes.network.ModNetwork;
import com.lithiumcraft.createresourcegeodes.registry.CatalystRegistryCache;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystDataProvider;
import com.lithiumcraft.createresourcegeodes.util.CatalystShapeTasks;
import net.minecraft.core.BlockPos;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.nbt.CompoundTag;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.core.HolderLookup;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.sounds.SoundSource;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.entity.BlockEntity;
import net.minecraft.world.level.block.state.BlockState;

import javax.annotation.Nullable;
import java.util.Optional;

public class CatalystBlockEntity extends BlockEntity implements CatalystDataProvider {
    private ResourceLocation catalystId;
    private int currentCooldown = -1;
    private int cachedTier = -1;
    private int cooldownTicksRemaining = 0;
    public int clientSyncedCooldown = 0;
    public int clientSyncedTier = 0;
    public Optional<ResourceLocation> clientSyncedAgitatorItem = Optional.empty();
    private boolean userPlaced = false;

    public CatalystBlockEntity(BlockPos pos, BlockState state) {
        super(ModBlockEntities.CATALYST.get(), pos, state);
    }

    public void setUserPlaced(boolean userPlaced) {
        this.userPlaced = userPlaced;
        setChanged();
        if (level != null && !level.isClientSide) {
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }

    public boolean isUserPlaced() {
        return userPlaced;
    }

    public void setCatalystId(ResourceLocation id, ServerLevel level) {
        this.catalystId = id;

        var registry = level.registryAccess().registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);
        var def = registry.get(id);

        if (def != null) {
            currentCooldown = def.cooldownTicks();
            cachedTier = def.minimumTier();
//        System.out.println("[CatalystBE] Loaded from registry: " + id);
        } else {
            currentCooldown = getDefaultCooldown();
            cachedTier = getDefaultMinimumTier();
//        System.out.println("[CatalystBE] Using fallback for: " + id);
        }

        setChanged();

        // ✅ Convert custom agitator item → ResourceLocation, if defined
        Optional<ResourceLocation> agitatorItem = Optional.empty();
        if (def != null && def.customAgitatorItem() != null) {
            agitatorItem = Optional.of(BuiltInRegistries.ITEM.getKey(def.customAgitatorItem()));
        }

        ModNetwork.sendCatalystSync(
                new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier, agitatorItem),
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
//                System.out.println("[CatalystBE] Repairing old catalyst at " + worldPosition);
                ensureCachedTier();
                setCatalystId(id, level);
                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3); // <--- force client refresh
            }
        }

        if (cooldownTicksRemaining > 0) {
            cooldownTicksRemaining--;
            setChanged();

            if (cooldownTicksRemaining % 20 == 0) {
                CatalystGeneratorDefinition def = getGeneratorDefinition();
                Optional<ResourceLocation> agitatorItem = Optional.empty();
                if (def != null && def.customAgitatorItem() != null) {
                    agitatorItem = Optional.of(BuiltInRegistries.ITEM.getKey(def.customAgitatorItem()));
                }

                ModNetwork.sendCatalystSync(
                        new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier, agitatorItem),
                        level,
                        worldPosition
                );

                level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3); // <--- force client refresh
            }
        }
    }


    public void resetCooldown() {
//        System.out.println("[CatalystBE] Cooldown reset to " + cachedCooldown + " at " + worldPosition);

        this.cooldownTicksRemaining = currentCooldown;
        this.setChanged();
    }

    public CompoundTag saveCustomData(HolderLookup.Provider provider) {
        CompoundTag tag = new CompoundTag();
        this.saveAdditional(tag, provider);
        if (!tag.contains("id")) {
            tag.putString("id", BuiltInRegistries.BLOCK_ENTITY_TYPE.getKey(this.getType()).toString());
        }

        return tag;
    }

    public void loadCustomData(CompoundTag tag, HolderLookup.Provider provider) {
        this.loadAdditional(tag, provider);
        this.setChanged();
    }

    @Override
    protected void saveAdditional(CompoundTag tag, HolderLookup.Provider provider) {
        if (catalystId != null) {
            tag.putString("CatalystId", catalystId.toString());
        }

        tag.putInt("CachedCooldown", currentCooldown);
        tag.putInt("CachedTier", cachedTier);
        tag.putInt("RemainingCooldown", cooldownTicksRemaining);
        tag.putBoolean("UserPlaced", userPlaced);
    }

    @Override
    public void loadAdditional(CompoundTag tag, HolderLookup.Provider provider) {
//        System.out.println("[Client] loadAdditional() called with tag = " + tag);

        if (tag.contains("CatalystId")) {
            catalystId = ResourceLocation.parse(tag.getString("CatalystId"));
//            System.out.println("[Client] Parsed CatalystId = " + catalystId);
        }

        currentCooldown = tag.getInt("CachedCooldown");
        cachedTier = tag.getInt("CachedTier");
        cooldownTicksRemaining = tag.getInt("RemainingCooldown");
        this.userPlaced = tag.getBoolean("UserPlaced");
    }



    @Override
    public void onLoad() {
        super.onLoad();

        if (!level.isClientSide && level instanceof ServerLevel serverLevel) {
            if (getBlockState().getBlock() instanceof CatalystDataProvider provider) {
                cachedTier = provider.getMinimumTier(serverLevel);
                currentCooldown = provider.getCooldown(serverLevel);
            }

            CatalystGeneratorDefinition def = getGeneratorDefinition();
            Optional<ResourceLocation> agitatorItem = Optional.empty();
            if (def != null && def.customAgitatorItem() != null) {
                agitatorItem = Optional.of(BuiltInRegistries.ITEM.getKey(def.customAgitatorItem()));
            }

            ModNetwork.sendCatalystSync(
                    new ClientboundSyncCatalystDataPacket(worldPosition, cooldownTicksRemaining, cachedTier, agitatorItem),
                    serverLevel,
                    worldPosition
            );

            setChanged();
            level.sendBlockUpdated(worldPosition, getBlockState(), getBlockState(), 3);
        }
    }


    private void ensureCachedTier() {
        if (cachedTier < 0 && level instanceof ServerLevel serverLevel) {
            if (getBlockState().getBlock() instanceof CatalystDataProvider provider) {
                cachedTier = provider.getMinimumTier(serverLevel);
            }
        }
    }

    public boolean tryActivate(Level level, ItemStack usedStack, @Nullable Player player) {
        if (!(level instanceof ServerLevel server)) return false;

        if (cooldownTicksRemaining > 0) {
            BlockPos pos = getBlockPos();
            server.playSound(null, pos.getX(), pos.getY(), pos.getZ(), SoundEvents.NOTE_BLOCK_BASS, SoundSource.BLOCKS, 1.0f, 1.0f);
            server.sendParticles(
                    ParticleTypes.SMOKE,
                    getBlockPos().getX() + 0.5,
                    getBlockPos().getY() + 1.2,
                    getBlockPos().getZ() + 0.5,
                    8, 0.2, 0.2, 0.2, 0.01
            );
            return false;
        }

        CatalystGeneratorDefinition def = getGeneratorDefinition();
        if (def == null) return false;

        Block generator = def.generatorBlock();
        CatalystShape shape = def.shape();
        int radius = def.radius();
        float fill = def.fillPercentage();

        switch (shape) {
            case SPHERE -> CatalystShapeTasks.queueSphere(level, getBlockPos(), generator, radius, fill);
            case CUBE -> CatalystShapeTasks.queueCube(level, getBlockPos(), generator, radius, fill);
        }

        cooldownTicksRemaining = currentCooldown;

        if (player != null && !player.isCreative()) {
            usedStack.hurtAndBreak(1, player, null);
        }

        return true;
    }

    public boolean isOnCooldown() {
        return cooldownTicksRemaining > 0;
    }

//    public CompoundTag getUpdateTag() {
//        CompoundTag tag = new CompoundTag();
//        if (catalystId != null) {
//            tag.putString("CatalystId", catalystId.toString());
//        }
//        return tag;
//    }
//
//    public void handleUpdateTag(CompoundTag tag) {
//        System.out.println("[Jade Sync] handleUpdateTag called with tag: " + tag);
//
//        if (tag.contains("CatalystId")) {
//            catalystId = ResourceLocation.parse(tag.getString("CatalystId"));
//        }
//    }

    public CatalystGeneratorDefinition getGeneratorDefinition() {
        if (catalystId == null) return null;

        CatalystGeneratorDefinition def;
        if (level.isClientSide) {
            def = CatalystRegistryCache.BY_ID.get(catalystId);
        } else {
            def = level.registryAccess()
                    .registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY)
                    .get(catalystId);
        }

        // ✅ Fallback to hardcoded defaults
        if (def == null && getBlockState().getBlock() instanceof CatalystDataProvider provider) {
            return new CatalystGeneratorDefinition(
                    provider.getDefaultGeneratorBlock(),
                    provider.getDefaultCooldown(),
                    provider.getDefaultShape(),
                    provider.getDefaultRadius(),
                    provider.getDefaultFillPercentage(),
                    provider.getDefaultMinimumTier(),
                    null // ← no custom agitator
            );
        }

        return def;
    }


//    @Override
//    public Packet<ClientGamePacketListener> getUpdatePacket() {
//        System.out.println("[Server] getUpdatePacket() called for " + worldPosition);
//        return ClientboundBlockEntityDataPacket.create(this, (be, provider) -> {
//            CompoundTag tag = new CompoundTag();
//            this.saveAdditional(tag, provider);
//            System.out.println("[Server] Sending CatalystId = " + tag.getString("CatalystId"));
//            return tag;
//        });
//    }

//    public void onDataPacket(Connection net, ClientboundBlockEntityDataPacket pkt) {
//        CompoundTag tag = pkt.getTag();
//        System.out.println("[Client] onDataPacket received tag: " + tag);
//        this.loadAdditional(tag, level.registryAccess());
//    }
}
