package com.lithiumcraft.createresourcegeodes.event;

import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.network.ClientboundCatalystRegistryPacket;
import com.lithiumcraft.createresourcegeodes.registry.CatalystRegistryCache;
import com.lithiumcraft.createresourcegeodes.registry.ModRegistries;
import com.lithiumcraft.createresourcegeodes.util.CatalystShapeTasks;
import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.core.Registry;
import net.minecraft.core.RegistryAccess;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.server.MinecraftServer;
import net.minecraft.server.level.ServerLevel;
import net.minecraft.server.level.ServerPlayer;
import net.minecraft.world.entity.npc.VillagerTrades;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.item.Items;
import net.minecraft.world.item.trading.ItemCost;
import net.minecraft.world.item.trading.MerchantOffer;
import net.neoforged.bus.api.SubscribeEvent;
import net.neoforged.fml.common.EventBusSubscriber;
import net.neoforged.fml.event.lifecycle.FMLCommonSetupEvent;
import net.neoforged.neoforge.client.event.ClientPlayerNetworkEvent;
import net.neoforged.neoforge.event.OnDatapackSyncEvent;
import net.neoforged.neoforge.event.entity.player.PlayerEvent;
import net.neoforged.neoforge.event.server.ServerStartedEvent;
import net.neoforged.neoforge.event.tick.ServerTickEvent;
import net.neoforged.neoforge.event.village.WandererTradesEvent;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;

@EventBusSubscriber(modid = CreateResourceGeodes.MOD_ID, bus = EventBusSubscriber.Bus.GAME)
public class ModEvents {

    @SubscribeEvent
    public static void addCustomWanderingTrades(WandererTradesEvent event) {
        List<VillagerTrades.ItemListing> genericTrades = event.getGenericTrades();
        List<VillagerTrades.ItemListing> rareTrades = event.getRareTrades();

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(ModItems.CATALYST_ACTIVATOR_WAND_GEM.get(), 1), 1, 10, 0.2f));

        rareTrades.add((pTrader, pRandom) -> new MerchantOffer(
                new ItemCost(Items.EMERALD, 16),
                new ItemStack(ModItems.CATALYST_ACTIVATOR_WAND_SHAFT.get(), 1), 1, 10, 0.2f));
    }

    @SubscribeEvent
    public static void onServerTick(ServerTickEvent.Post event) {
        MinecraftServer server = event.getServer();
        CatalystShapeTasks.tick(server);
    }

    @SubscribeEvent
    public static void onServerStarted(ServerStartedEvent event) {
        RegistryAccess access = event.getServer().registryAccess();
        Registry<CatalystGeneratorDefinition> registry = access.registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);

        Map<ResourceLocation, CatalystGeneratorDefinition> toSend = registry.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().location(), Map.Entry::getValue));

        ClientboundCatalystRegistryPacket packet = new ClientboundCatalystRegistryPacket(toSend);

        for (ServerPlayer player : event.getServer().getPlayerList().getPlayers()) {
            player.connection.send(packet);
        }
    }

    @SubscribeEvent
    public static void onPlayerLogin(PlayerEvent.PlayerLoggedInEvent event) {
        if (!(event.getEntity() instanceof ServerPlayer player)) return;

        RegistryAccess access = player.server.registryAccess();
        Registry<CatalystGeneratorDefinition> registry = access.registryOrThrow(ModRegistries.CATALYST_DEFINITION_KEY);

        Map<ResourceLocation, CatalystGeneratorDefinition> toSend = registry.entrySet().stream()
                .collect(Collectors.toMap(e -> e.getKey().location(), Map.Entry::getValue));

        player.connection.send(new ClientboundCatalystRegistryPacket(toSend));
    }

    @SubscribeEvent
    public static void onClientDisconnect(ClientPlayerNetworkEvent.LoggingOut event) {
        CatalystRegistryCache.BY_ID.clear();
        CatalystRegistryCache.DEFINITIONS.clear();
    }
}
