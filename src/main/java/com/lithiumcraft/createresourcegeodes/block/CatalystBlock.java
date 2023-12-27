package com.lithiumcraft.createresourcegeodes.block;

import com.mojang.logging.LogUtils;
import net.minecraft.world.level.block.Block;
import org.slf4j.Logger;

import java.util.Random;

abstract public class CatalystBlock extends Block {

    protected static final Logger LOGGER = LogUtils.getLogger();
    public static final int TICKS = 120; // time it takes at minimum

    public CatalystBlock(Properties pProperties) {
        super(pProperties);
    }
}
