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

package com.lithiumcraft.createresourcegeodes.datagen;

import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.block.ModBlocks;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

import static com.lithiumcraft.createresourcegeodes.registry.CommonBlockTags.*;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get())
                .add(ModBlocks.GENERIC_CATALYST_1.get())
                .add(ModBlocks.GENERIC_CATALYST_2.get())
                .add(ModBlocks.GENERIC_CATALYST_3.get())
                .add(ModBlocks.GENERIC_CATALYST_4.get())
                .add(ModBlocks.GENERIC_CATALYST_5.get())
                .add(ModBlocks.GENERIC_CATALYST_6.get())
                .add(ModBlocks.GENERIC_CATALYST_7.get())
                .add(ModBlocks.GENERIC_CATALYST_8.get())
                .add(ModBlocks.GENERIC_CATALYST_9.get())
                .add(ModBlocks.GENERIC_CATALYST_10.get())
                .add(ModBlocks.GENERIC_CATALYST_11.get())
                .add(ModBlocks.GENERIC_CATALYST_12.get());

        tag(RELOCATION_NOT_SUPPORTED)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get())
                .add(ModBlocks.GENERIC_CATALYST_1.get())
                .add(ModBlocks.GENERIC_CATALYST_2.get())
                .add(ModBlocks.GENERIC_CATALYST_3.get())
                .add(ModBlocks.GENERIC_CATALYST_4.get())
                .add(ModBlocks.GENERIC_CATALYST_5.get())
                .add(ModBlocks.GENERIC_CATALYST_6.get())
                .add(ModBlocks.GENERIC_CATALYST_7.get())
                .add(ModBlocks.GENERIC_CATALYST_8.get())
                .add(ModBlocks.GENERIC_CATALYST_9.get())
                .add(ModBlocks.GENERIC_CATALYST_10.get())
                .add(ModBlocks.GENERIC_CATALYST_11.get())
                .add(ModBlocks.GENERIC_CATALYST_12.get());

        tag(WITHER_IMMUNE)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get())
                .add(ModBlocks.GENERIC_CATALYST_1.get())
                .add(ModBlocks.GENERIC_CATALYST_2.get())
                .add(ModBlocks.GENERIC_CATALYST_3.get())
                .add(ModBlocks.GENERIC_CATALYST_4.get())
                .add(ModBlocks.GENERIC_CATALYST_5.get())
                .add(ModBlocks.GENERIC_CATALYST_6.get())
                .add(ModBlocks.GENERIC_CATALYST_7.get())
                .add(ModBlocks.GENERIC_CATALYST_8.get())
                .add(ModBlocks.GENERIC_CATALYST_9.get())
                .add(ModBlocks.GENERIC_CATALYST_10.get())
                .add(ModBlocks.GENERIC_CATALYST_11.get())
                .add(ModBlocks.GENERIC_CATALYST_12.get());

        tag(DRAGON_IMMUNE)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get())
                .add(ModBlocks.GENERIC_CATALYST_1.get())
                .add(ModBlocks.GENERIC_CATALYST_2.get())
                .add(ModBlocks.GENERIC_CATALYST_3.get())
                .add(ModBlocks.GENERIC_CATALYST_4.get())
                .add(ModBlocks.GENERIC_CATALYST_5.get())
                .add(ModBlocks.GENERIC_CATALYST_6.get())
                .add(ModBlocks.GENERIC_CATALYST_7.get())
                .add(ModBlocks.GENERIC_CATALYST_8.get())
                .add(ModBlocks.GENERIC_CATALYST_9.get())
                .add(ModBlocks.GENERIC_CATALYST_10.get())
                .add(ModBlocks.GENERIC_CATALYST_11.get())
                .add(ModBlocks.GENERIC_CATALYST_12.get());

        tag(BLACKLISTED_SPATIAL)
                .add(ModBlocks.ASURINE_CATALYST.get())
                .add(ModBlocks.CRIMSITE_CATALYST.get())
                .add(ModBlocks.OCHRUM_CATALYST.get())
                .add(ModBlocks.VERIDIUM_CATALYST.get())
                .add(ModBlocks.SKY_STONE_CATALYST.get())
                .add(ModBlocks.GENERIC_CATALYST_1.get())
                .add(ModBlocks.GENERIC_CATALYST_2.get())
                .add(ModBlocks.GENERIC_CATALYST_3.get())
                .add(ModBlocks.GENERIC_CATALYST_4.get())
                .add(ModBlocks.GENERIC_CATALYST_5.get())
                .add(ModBlocks.GENERIC_CATALYST_6.get())
                .add(ModBlocks.GENERIC_CATALYST_7.get())
                .add(ModBlocks.GENERIC_CATALYST_8.get())
                .add(ModBlocks.GENERIC_CATALYST_9.get())
                .add(ModBlocks.GENERIC_CATALYST_10.get())
                .add(ModBlocks.GENERIC_CATALYST_11.get())
                .add(ModBlocks.GENERIC_CATALYST_12.get());
    }

}