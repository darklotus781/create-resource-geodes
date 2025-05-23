package com.lithiumcraft.createresourcegeodes.data;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.mojang.serialization.Codec;
import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import net.minecraft.core.registries.BuiltInRegistries;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;

import javax.annotation.Nullable;
import java.util.Optional;

public record CatalystGeneratorDefinition(
        Block generatorBlock,
        int cooldownTicks,
        CatalystShape shape,
        int radius,
        float fillPercentage,
        int minimumTier,
        @Nullable Item customAgitatorItem
) {
    public static final MapCodec<CatalystGeneratorDefinition> MAP_CODEC = RecordCodecBuilder.mapCodec(instance -> instance.group(
            BuiltInRegistries.BLOCK.byNameCodec().fieldOf("generator").forGetter(CatalystGeneratorDefinition::generatorBlock),
            Codec.INT.optionalFieldOf("cooldown", 120).forGetter(CatalystGeneratorDefinition::cooldownTicks),
            CatalystShape.CODEC.optionalFieldOf("shape", CatalystShape.SPHERE).forGetter(CatalystGeneratorDefinition::shape),
            Codec.INT.optionalFieldOf("radius", 3).forGetter(CatalystGeneratorDefinition::radius),
            Codec.FLOAT.optionalFieldOf("fill_percentage", 1.0f).forGetter(CatalystGeneratorDefinition::fillPercentage),
            Codec.INT.optionalFieldOf("minimum_tier", 1).forGetter(CatalystGeneratorDefinition::minimumTier),
            BuiltInRegistries.ITEM.byNameCodec().optionalFieldOf("custom_agitator_item").forGetter(def -> Optional.ofNullable(def.customAgitatorItem()))
    ).apply(instance, (generator, cooldown, shape, radius, fill, tier, optionalItem) ->
            new CatalystGeneratorDefinition(generator, cooldown, shape, radius, fill, tier, optionalItem.orElse(null))
    ));

    public static final Codec<CatalystGeneratorDefinition> CODEC = MAP_CODEC.codec();

    public boolean isCustomAgitatorBased() {
        return customAgitatorItem != null;
    }
}

