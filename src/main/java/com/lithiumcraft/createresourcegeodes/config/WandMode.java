package com.lithiumcraft.createresourcegeodes.config;

public enum WandMode {
    MOVE,
    BREAK;

    public WandMode next() {
        return values()[(this.ordinal() + 1) % values().length];
    }
}