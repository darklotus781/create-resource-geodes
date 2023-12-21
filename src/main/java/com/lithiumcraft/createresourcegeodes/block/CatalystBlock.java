package com.lithiumcraft.createresourcegeodes.block;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

abstract public class CatalystBlock extends Block {

    private static final Logger LOGGER = LogUtils.getLogger();

//    public static Block generatorBlock;

    public CatalystBlock(Properties pProperties) {
        super(pProperties);
//        generatorBlock = !ModList.get().isLoaded("create") ?
//                ModBlocks.FAUX_CRIMSITE.get() :
//                RegistryObject.create(new ResourceLocation("create:crimsite"), ForgeRegistries.BLOCKS).get();
    }

}
