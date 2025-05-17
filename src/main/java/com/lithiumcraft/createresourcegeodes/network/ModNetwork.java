package com.lithiumcraft.createresourcegeodes.network;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.entity.CatalystBlockEntity;
import net.minecraft.client.Minecraft;
import net.minecraft.core.BlockPos;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.world.level.ChunkPos;
import net.neoforged.neoforge.network.PacketDistributor;
import net.neoforged.neoforge.network.event.RegisterPayloadHandlersEvent;

public class ModNetwork {
    public static void registerPackets(RegisterPayloadHandlersEvent event) {
//        System.out.println("[ModNetwork] Registering packets...");

        var registrar = event.registrar(CreateResourceGeodes.MOD_ID);
        registrar.playToClient(
                ClientboundSyncCatalystDataPacket.TYPE,
                ClientboundSyncCatalystDataPacket.STREAM_CODEC,
                (payload, context) -> {
                    Minecraft.getInstance().execute(() -> {
                        var level = Minecraft.getInstance().level;
//                        System.out.println("[Client] Received packet: " + payload.cooldownRemaining());
                        if (level != null && level.getBlockEntity(payload.pos()) instanceof CatalystBlockEntity catalyst) {
                            catalyst.clientSyncedCooldown = payload.cooldownRemaining();
                            catalyst.clientSyncedTier = payload.requiredTier();
//                            System.out.println("[Client] Synced to: " + catalyst.clientSyncedCooldown);
                        }
                    });
                }
        );
    }

    public static void sendCatalystSync(ClientboundSyncCatalystDataPacket packet, ServerLevel level, BlockPos pos) {
        ChunkPos chunkPos = new ChunkPos(pos);
        PacketDistributor.sendToPlayersTrackingChunk(level, chunkPos, packet);
    }
}
