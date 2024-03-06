package com.lithiumcraft.createresourcegeodes.block;


import com.lithiumcraft.createresourcegeodes.CreateResourceGeodes;
import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.world.item.BlockItem;
import net.minecraft.world.item.Item;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.SoundType;
import net.minecraft.world.level.block.state.BlockBehaviour;
import net.minecraft.world.level.material.MapColor;
import net.minecraftforge.eventbus.api.IEventBus;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.DeferredRegister;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.function.Supplier;

@Mod.EventBusSubscriber(bus = Mod.EventBusSubscriber.Bus.MOD)
public class ModBlocks {
    public static final DeferredRegister<Block> BLOCKS =
            DeferredRegister.create(ForgeRegistries.BLOCKS, CreateResourceGeodes.MOD_ID);

    public static final RegistryObject<Block> FAUX_ASURINE = registerBlock("faux_asurine",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F).mapColor(MapColor.COLOR_BLUE)));
    public static final RegistryObject<Block> FAUX_CRIMSITE = registerBlock("faux_crimsite",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F).mapColor(MapColor.COLOR_RED)));
    public static final RegistryObject<Block> FAUX_OCHRUM = registerBlock("faux_ochrum",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F).mapColor(MapColor.TERRACOTTA_YELLOW)));
    public static final RegistryObject<Block> FAUX_VERIDIUM = registerBlock("faux_veridium",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(1.0F).mapColor(MapColor.WARPED_NYLIUM)));
    public static final RegistryObject<Block> FAUX_SKY_STONE = registerBlock("faux_sky_stone",
            () -> new Block(BlockBehaviour.Properties.copy(Blocks.DEEPSLATE).sound(SoundType.DEEPSLATE).requiresCorrectToolForDrops().strength(1.5F).mapColor(MapColor.TERRACOTTA_BLACK)));
    public static final RegistryObject<Block> ASURINE_CATALYST = registerBlock("asurine_catalyst",
            () -> new AsurineCatalystBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.AMETHYST).lightLevel(s -> 10).mapColor(MapColor.COLOR_BLUE).noLootTable()));
    public static final RegistryObject<Block> CRIMSITE_CATALYST = registerBlock("crimsite_catalyst",
            () -> new CrimsiteCatalystBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.DEEPSLATE).lightLevel(s -> 10).mapColor(MapColor.COLOR_RED).noLootTable()));
    public static final RegistryObject<Block> OCHRUM_CATALYST = registerBlock("ochrum_catalyst",
            () -> new OchrumCatalystBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.DEEPSLATE).lightLevel(s -> 10).mapColor(MapColor.TERRACOTTA_YELLOW).noLootTable()));
    public static final RegistryObject<Block> VERIDIUM_CATALYST = registerBlock("veridium_catalyst",
            () -> new VeridiumCatalystBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.DEEPSLATE).lightLevel(s -> 10).mapColor(MapColor.WARPED_NYLIUM).noLootTable()));
    public static final RegistryObject<Block> SKY_STONE_CATALYST = registerBlock("sky_stone_catalyst",
            () -> new SkyStoneCatalystBlock(BlockBehaviour.Properties.copy(Blocks.BEDROCK).sound(SoundType.DEEPSLATE).lightLevel(s -> 10).mapColor(MapColor.TERRACOTTA_BLACK).noLootTable()));


    private static <T extends Block> RegistryObject<T> registerBlock(String name, Supplier<T> block) {
        RegistryObject<T> toReturn = BLOCKS.register(name, block);
        registerBlockItem(name, toReturn);
        return toReturn;
    }

    private static <T extends Block> void registerBlockItem(String name, RegistryObject<T> block) {
        ModItems.ITEMS.register(name, () -> new BlockItem(block.get(), new Item.Properties()));
    }

    public static void register(IEventBus eventBus) {
        BLOCKS.register(eventBus);
    }
}
