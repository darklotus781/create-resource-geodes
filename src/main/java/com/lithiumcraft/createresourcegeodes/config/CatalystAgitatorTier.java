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