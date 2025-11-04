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
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.data.PackOutput;
import net.minecraft.resources.ResourceLocation;
import net.neoforged.neoforge.client.model.generators.ItemModelBuilder;
import net.neoforged.neoforge.client.model.generators.ItemModelProvider;
import net.neoforged.neoforge.common.data.ExistingFileHelper;
import net.neoforged.neoforge.registries.DeferredItem;


public class ModItemModelProvider extends ItemModelProvider {
    public ModItemModelProvider(PackOutput output, ExistingFileHelper existingFileHelper) {
        super(output, CreateResourceGeodes.MOD_ID, existingFileHelper);
    }

    @Override
    protected void registerModels() {
//        handheldItem(ModItems.ACTIVATOR_WAND);
        simpleItem(ModItems.CATALYST_AGITATOR);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_2);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_3);
        simpleItem(ModItems.CATALYST_AGITATOR_TIER_4);
        simpleItem(ModItems.CATALYST_ACTIVATOR_WAND_GEM);
        simpleItem(ModItems.CATALYST_ACTIVATOR_WAND_SHAFT);
        simpleItem(ModItems.CATALYST_CORE);
    }

    private ItemModelBuilder simpleItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "item/" + item.getId().getPath()));
    }

    private ItemModelBuilder handheldItem(DeferredItem<?> item) {
        return withExistingParent(item.getId().getPath(),
                ResourceLocation.parse("item/handheld")).texture("layer0",
                ResourceLocation.fromNamespaceAndPath(CreateResourceGeodes.MOD_ID, "item/" + item.getId().getPath()));
    }
}