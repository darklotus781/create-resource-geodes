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
