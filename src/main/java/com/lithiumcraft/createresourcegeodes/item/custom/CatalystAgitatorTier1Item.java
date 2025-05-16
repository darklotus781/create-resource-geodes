package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;

public class CatalystAgitatorTier1Item extends BaseCatalystAgitatorItem {
    public CatalystAgitatorTier1Item(Properties properties) {
        super(properties);
    }

    @Override
    public CatalystAgitatorTier getTier() {
        return CatalystAgitatorTier.TIER_1;
    }
}
