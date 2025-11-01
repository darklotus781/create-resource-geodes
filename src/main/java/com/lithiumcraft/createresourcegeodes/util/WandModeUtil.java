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