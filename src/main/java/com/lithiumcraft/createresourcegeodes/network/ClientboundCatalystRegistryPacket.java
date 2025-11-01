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

import com.google.gson.JsonParser;
import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.mojang.serialization.JsonOps;
import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.ResourceLocation;

import java.util.HashMap;
import java.util.Map;

public record ClientboundCatalystRegistryPacket(Map<ResourceLocation, CatalystGeneratorDefinition> definitions)
        implements CustomPacketPayload {

    public static final ResourceLocation ID = ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "sync_catalyst_registry");
    public static final Type<ClientboundCatalystRegistryPacket> TYPE = new Type<>(ID);

    public static final StreamCodec<FriendlyByteBuf, ClientboundCatalystRegistryPacket> STREAM_CODEC =
            StreamCodec.of(
                (buf, packet) -> {
                    buf.writeVarInt(packet.definitions.size());
                    for (var entry : packet.definitions.entrySet()) {
                        buf.writeResourceLocation(entry.getKey());
                        CatalystGeneratorDefinition.CODEC.encodeStart(JsonOps.INSTANCE, entry.getValue())
                                .resultOrPartial(error -> CreateResourceGeodes.LOGGER.error("Failed to encode catalyst '{}': {}", entry.getKey(), error))
                                .ifPresent(json -> buf.writeUtf(json.toString()));
                    }
                },
                buf -> {
                    int size = buf.readVarInt();
                    Map<ResourceLocation, CatalystGeneratorDefinition> defs = new HashMap<>();
                    for (int i = 0; i < size; i++) {
                        ResourceLocation id = buf.readResourceLocation();
                        String json = buf.readUtf();
                        CatalystGeneratorDefinition.CODEC.parse(JsonOps.INSTANCE, JsonParser.parseString(json))
                                .resultOrPartial(err -> CreateResourceGeodes.LOGGER.error("Failed to decode catalyst '{}': {}", id, err))
                                .ifPresent(def -> defs.put(id, def));
                    }
                    return new ClientboundCatalystRegistryPacket(defs);
                }
            );

    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
