package com.lithiumcraft.createresourcegeodes.util;

import com.lithiumcraft.createresourcegeodes.component.ModDataComponents;
import com.lithiumcraft.createresourcegeodes.config.WandMode;
import net.minecraft.world.item.ItemStack;

public class WandModeUtil {
    private static final String DEFAULT_MODE = WandMode.MOVE.name();

    public static WandMode getMode(ItemStack stack) {
        String modeName = stack.get(ModDataComponents.WAND_MODE.get());
        try {
            return WandMode.valueOf(modeName != null ? modeName : DEFAULT_MODE);
        } catch (IllegalArgumentException e) {
            return WandMode.MOVE;
        }
    }

    public static void setMode(ItemStack stack, WandMode mode) {
        stack.set(ModDataComponents.WAND_MODE.get(), mode.name());
    }
}