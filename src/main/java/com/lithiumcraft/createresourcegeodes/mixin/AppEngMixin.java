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

package com.lithiumcraft.createresourcegeodes.mixin;

import appeng.core.AEConfig;
import appeng.core.definitions.AEBlocks;
import appeng.worldgen.meteorite.MeteoriteBlockPutter;
import appeng.worldgen.meteorite.MeteoritePlacer;
import com.lithiumcraft.createresourcegeodes.Config;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.BlockPos;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.state.BlockState;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Overwrite;
import org.spongepowered.asm.mixin.Shadow;

@Mixin(value = MeteoritePlacer.class, remap = false)
public abstract class AppEngMixin {
    @Shadow
    private final LevelAccessor level;
    @Shadow
    private MeteoriteBlockPutter putter;
    @Shadow
    private BlockPos pos;

    protected AppEngMixin(LevelAccessor level) {
        this.level = level;
    }

    /**
     * @author DarkLotus
     * @reason If enabled, we'll replace the Meteorite Chest with a Catalyst
     */
    @Overwrite
    private void placeChest() {
        BlockState meteorBlock = Config.replaceAe2Meteor ?
                ModBlocks.SKY_STONE_CATALYST.get().defaultBlockState() :
                AEBlocks.MYSTERIOUS_CUBE.block().defaultBlockState();
        if (AEConfig.instance().isSpawnPressesInMeteoritesEnabled()) {
            this.putter.put(this.level, this.pos, meteorBlock);
        }
    }
}
