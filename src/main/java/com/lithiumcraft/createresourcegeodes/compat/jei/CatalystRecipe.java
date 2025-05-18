package com.lithiumcraft.createresourcegeodes.compat.jei;

import com.lithiumcraft.createresourcegeodes.config.CatalystShape;
import com.lithiumcraft.createresourcegeodes.data.CatalystGeneratorDefinition;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.block.Block;

/**
 * JEI representation of a Catalyst recipe:
 * - Input: a catalyst item
 * - Output: the block it generates
 * - With metadata: cooldown, required agitator tier, shape
 */
public class CatalystRecipe {
    private final ItemStack catalystItem;
    private final Block generatorBlock;
    private final int tier;
    private final int cooldownTicks;
    private final CatalystShape shape;
    private final int radius;
    private final float fill;

    public CatalystRecipe(ItemStack catalystItem, CatalystGeneratorDefinition def) {
        this.catalystItem = catalystItem.copy();
        this.generatorBlock = def.generatorBlock();
        this.tier = def.minimumTier();
        this.cooldownTicks = def.cooldownTicks();
        this.shape = def.shape();
        this.radius = def.radius();
        this.fill = def.fillPercentage();
    }

    public int getRadius() { return radius; }
    public float getFillPercentage() { return fill; }

    public ItemStack getCatalystItem() {
        return catalystItem;
    }

    public Block getGeneratorBlock() {
        return generatorBlock;
    }

    public int getTier() {
        return tier;
    }

    public int getCooldownTicks() {
        return cooldownTicks;
    }

    public CatalystShape getShape() {
        return shape;
    }

    public ItemStack getAgitatorItem() {
        return switch (tier) {
            case 2 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_2.get());
            case 3 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_3.get());
            case 4 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_4.get());
            default -> new ItemStack(ModItems.CATALYST_AGITATOR.get()); // Tier 1
        };
    }
}
