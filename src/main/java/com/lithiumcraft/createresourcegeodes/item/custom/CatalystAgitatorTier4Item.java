package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;

public class CatalystAgitatorTier4Item extends BaseCatalystAgitatorItem {
    public CatalystAgitatorTier4Item(Properties properties) {
        super(properties);
    }

    @Override
    public CatalystAgitatorTier getTier() {
        return CatalystAgitatorTier.TIER_4;
    }
}
