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

package com.lithiumcraft.createresourcegeodes.network;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.registry.CatalystRegistryCache;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

import java.util.Map;

public class ModNetwork {

    public static void registerPackets(RegisterPayloadHandlersEvent event) {
        var registrar = event.registrar(CreateResourceGeodes.MOD_ID);

        // ✅ 1. Jade tooltip sync (per block)
        registrar.playToClient(
                ClientboundSyncCatalystDataPacket.TYPE,
                ClientboundSyncCatalystDataPacket.STREAM_CODEC,
                (payload, context) -> {
                    Minecraft.getInstance().execute(() -> {
                        var level = Minecraft.getInstance().level;
                        if (level != null && level.getBlockEntity(payload.pos()) instanceof CatalystBlockEntity catalyst) {
                            catalyst.clientSyncedCooldown = payload.cooldownRemaining();
                            catalyst.clientSyncedTier = payload.requiredTier();
                            catalyst.clientSyncedAgitatorItem = payload.requiredAgitatorItem();
                        }
                    });
                }
        );


        // ✅ 2. JEI catalyst registry sync
        registrar.playToClient(
                ClientboundCatalystRegistryPacket.TYPE,
                ClientboundCatalystRegistryPacket.STREAM_CODEC,
                (payload, context) -> {
                    Minecraft.getInstance().execute(() -> {
                        CatalystRegistryCache.updateFromClient(payload.definitions());
//                        System.out.println("[ModNetwork] ✅ Synced " + payload.definitions().size() + " catalyst definitions to client");
                    });
                }
        );
    }

    // ✅ Send block-specific tooltip sync to clients tracking the chunk
    public static void sendCatalystSync(ClientboundSyncCatalystDataPacket packet, ServerLevel level, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, packet);
    }

    // ✅ Send full catalyst registry to one player (e.g. during login or server start)
    public static void sendCatalystRegistrySync(ServerPlayer player, Map<ResourceLocation, CatalystGeneratorDefinition> defs) {
        player.connection.send(new ClientboundCatalystRegistryPacket(defs));
    }
}