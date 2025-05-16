package com.lithiumcraft.createresourcegeodes.sound;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvent;
import net.neoforged.bus.api.IEventBus;
import net.neoforged.neoforge.registries.DeferredRegister;

import java.util.function.Supplier;

public class ModSounds {
    public static final DeferredRegister<SoundEvent> SOUND_EVENTS =
            DeferredRegister.create(BuiltInRegistries.SOUND_EVENT, CreateResourceGeodes.MOD_ID);

    public static final Supplier<SoundEvent> CATALYST_BLOCK_TELEPORT = registerSoundEvent("catalyst_block_teleport");
    public static final Supplier<SoundEvent> AGITATOR_INVALID_TIER = registerSoundEvent("agitator_invalid_tier");

    private static Supplier<SoundEvent> registerSoundEvent(String name) {
        ResourceLocation id = ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, name);
        return SOUND_EVENTS.register(name, () -> SoundEvent.createVariableRangeEvent(id));
    }

    public static void register(IEventBus eventBus) {
        SOUND_EVENTS.register(eventBus);
    }
}
