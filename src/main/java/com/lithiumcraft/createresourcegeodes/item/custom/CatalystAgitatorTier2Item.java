package com.lithiumcraft.createresourcegeodes.item.custom;

import com.lithiumcraft.createresourcegeodes.config.CatalystAgitatorTier;

public class CatalystAgitatorTier2Item extends BaseCatalystAgitatorItem {
    public CatalystAgitatorTier2Item(Properties properties) {
        super(properties);
    }

    @Override
    public CatalystAgitatorTier getTier() {
        return CatalystAgitatorTier.TIER_2;
    }
}
