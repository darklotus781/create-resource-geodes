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
    private final CatalystGeneratorDefinition definition;


    public CatalystRecipe(ItemStack catalystItem, CatalystGeneratorDefinition def) {
        this.catalystItem = catalystItem.copy();
        this.generatorBlock = def.generatorBlock();
        this.definition = def;
    }

    public CatalystGeneratorDefinition getDefinition() {
        return definition;
    }

    public int getTier() {
        return definition.minimumTier();
    }

    public int getCooldownTicks() {
        return definition.cooldownTicks();
    }

    public CatalystShape getShape() {
        return definition.shape();
    }

    public int getRadius() {
        return definition.radius();
    }

    public float getFillPercentage() {
        return definition.fillPercentage();
    }

    public ItemStack getCatalystItem() {
        return catalystItem;
    }


    public ItemStack getAgitatorItem() {
        if (definition.isCustomAgitatorBased()) {
            return new ItemStack(definition.customAgitatorItem());
        }

        return switch (definition.minimumTier()) {
            case 2 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_2.get());
            case 3 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_3.get());
            case 4 -> new ItemStack(ModItems.CATALYST_AGITATOR_TIER_4.get());
            default -> new ItemStack(ModItems.CATALYST_AGITATOR.get()); // Tier 1
        };
    }
}
