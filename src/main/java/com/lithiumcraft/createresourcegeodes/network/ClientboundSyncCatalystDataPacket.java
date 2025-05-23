package com.lithiumcraft.createresourcegeodes.network;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import net.minecraft.core.BlockPos;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import javax.annotation.Nullable;
import java.util.Optional;

public record ClientboundSyncCatalystDataPacket(
        BlockPos pos,
        int cooldownRemaining,
        int requiredTier,
        Optional<ResourceLocation> requiredAgitatorItem
) implements CustomPacketPayload {

    public static final ResourceLocation ID =
            ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "sync_catalyst");

    public static final Type<ClientboundSyncCatalystDataPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ClientboundSyncCatalystDataPacket> STREAM_CODEC =
            StreamCodec.composite(
                    BlockPos.STREAM_CODEC, ClientboundSyncCatalystDataPacket::pos,
                    ByteBufCodecs.VAR_INT, ClientboundSyncCatalystDataPacket::cooldownRemaining,
                    ByteBufCodecs.VAR_INT, ClientboundSyncCatalystDataPacket::requiredTier,
                    ByteBufCodecs.optional(ResourceLocation.STREAM_CODEC), ClientboundSyncCatalystDataPacket::requiredAgitatorItem,
                    ClientboundSyncCatalystDataPacket::new
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
