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
import com.lithiumcraft.createresourcegeodes.registry.ModTags;
import net.minecraft.core.HolderLookup;
import net.minecraft.data.PackOutput;
import net.minecraft.tags.BlockTags;
import net.neoforged.neoforge.common.data.BlockTagsProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import org.jetbrains.annotations.Nullable;

import java.util.concurrent.CompletableFuture;

public class ModBlockTagProvider extends BlockTagsProvider {
    public ModBlockTagProvider(PackOutput output, CompletableFuture<HolderLookup.Provider> lookupProvider, @Nullable ExistingFileHelper existingFileHelper) {
        super(output, lookupProvider, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void addTags(HolderLookup.Provider provider) {
        tag(ModTags.Blocks.CATALYSTS)
                .addOptional(CreateResourceGeodes.rl("asurine_catalyst"))
                .addOptional(CreateResourceGeodes.rl("crimsite_catalyst"))
                .addOptional(CreateResourceGeodes.rl("ochrum_catalyst"))
                .addOptional(CreateResourceGeodes.rl("veridium_catalyst"))
                .addOptional(CreateResourceGeodes.rl("sky_stone_catalyst"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_1"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_2"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_3"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_4"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_5"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_6"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_7"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_8"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_9"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_10"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_11"))
                .addOptional(CreateResourceGeodes.rl("generic_catalyst_12"));

        tag(BlockTags.FEATURES_CANNOT_REPLACE)
                .addTag(ModTags.Blocks.CATALYSTS);

        tag(ModTags.Blocks.RELOCATION_NOT_SUPPORTED)
                .addTag(ModTags.Blocks.CATALYSTS);

        tag(BlockTags.WITHER_IMMUNE)
                .addTag(ModTags.Blocks.CATALYSTS);

        tag(BlockTags.DRAGON_IMMUNE)
                .addTag(ModTags.Blocks.CATALYSTS);

        tag(ModTags.Blocks.BLACKLISTED_SPATIAL)
                .addTag(ModTags.Blocks.CATALYSTS);
    }
}