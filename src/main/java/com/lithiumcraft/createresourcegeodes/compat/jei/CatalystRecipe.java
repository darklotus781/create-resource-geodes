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
