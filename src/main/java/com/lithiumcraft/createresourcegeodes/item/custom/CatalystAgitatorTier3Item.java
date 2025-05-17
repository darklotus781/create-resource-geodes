package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;

public class CatalystAgitatorTier3Item extends BaseCatalystAgitatorItem {
    public CatalystAgitatorTier3Item(Properties properties) {
        super(properties);
    }

    @Override
    public CatalystAgitatorTier getTier() {
        return CatalystAgitatorTier.TIER_3;
    }
}
