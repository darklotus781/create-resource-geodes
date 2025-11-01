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

package com.lithiumcraft.createresourcegeodes.config;

import com.mojang.serialization.Codec;

public enum CatalystAgitatorTier {
    TIER_1(1),
    TIER_2(2),
    TIER_3(3),
    TIER_4(4);

    private final int level;
    CatalystAgitatorTier(int level) { this.level = level; }
    public int getLevel() { return level; }

    public static CatalystAgitatorTier fromLevel(int level) {
        for (var t : values()) if (t.level == level) return t;
        return TIER_1;
    }
}