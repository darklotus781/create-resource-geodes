package com.lithiumcraft.createresourcegeodes;

import com.mojang.logging.LogUtils;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.event.server.ServerStartingEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import org.slf4j.Logger;

public class EventListener {
    private static final Logger LOGGER = LogUtils.getLogger();

    @SubscribeEvent
    public void onServerStarting(ServerStartingEvent event) {
        // Do something when the server starts
    }

    @SubscribeEvent
    public void onServerTick(TickEvent.LevelTickEvent event) {

        if (event.phase == TickEvent.Phase.START && !event.level.isClientSide
                && event.level == event.level.getServer().overworld()) {
            // Do things at the start of every tick here...
        }
    }

}
