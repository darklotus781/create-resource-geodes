package com.lithiumcraft.createresourcegeodes.data;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.level.block.Block;

public record CatalystGeneratorDefinition(
        Block generatorBlock,
        int cooldownTicks,
        CatalystShape shape,
        int radius,
        float fillPercentage,
        int minimumTier

) {
    public static final MapCodec<CatalystGeneratorDefinition> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("generator").forGetter(CatalystGeneratorDefinition::generatorBlock),
            Codec.INT.optionalFieldOf("cooldown", 120).forGetter(CatalystGeneratorDefinition::cooldownTicks),
            CatalystShape.CODEC.optionalFieldOf("shape", CatalystShape.SPHERE).forGetter(CatalystGeneratorDefinition::shape),
            Codec.INT.optionalFieldOf("radius", 3).forGetter(CatalystGeneratorDefinition::radius),
            Codec.FLOAT.optionalFieldOf("fill_percentage", 1.0f).forGetter(CatalystGeneratorDefinition::fillPercentage),
            Codec.INT.optionalFieldOf("minimum_tier", 1).forGetter(CatalystGeneratorDefinition::minimumTier)
    ).apply(instance, CatalystGeneratorDefinition::new));

    public static final Codec<CatalystGeneratorDefinition> CODEC = MAP_CODEC.codec();
}

