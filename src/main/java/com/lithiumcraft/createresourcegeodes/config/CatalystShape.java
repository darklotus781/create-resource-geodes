package com.lithiumcraft.createresourcegeodes.config;

import com.mojang.serialization.Codec;

public enum CatalystShape {
    CUBE,
    SPHERE;

    public static final Codec<CatalystShape> CODEC = Codec.STRING.xmap(
            CatalystShape::valueOf,
            CatalystShape::name
    );
}
