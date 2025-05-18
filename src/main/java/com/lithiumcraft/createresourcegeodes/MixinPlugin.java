package com.lithiumcraft.createresourcegeodes;

import net.neoforged.fml.loading.LoadingModList;
import org.objectweb.asm.tree.ClassNode;
import org.spongepowered.asm.mixin.extensibility.IMixinConfigPlugin;
import org.spongepowered.asm.mixin.extensibility.IMixinInfo;

import java.util.List;
import java.util.Set;

public class MixinPlugin implements IMixinConfigPlugin {

    private boolean isAE2Loaded = false;
//    private boolean isPonderLoaded = false;

    @Override
    public void onLoad(String mixinPackage) {
        var modList = LoadingModList.get();
        isAE2Loaded = modList.getModFileById("ae2") != null;
//        isPonderLoaded = modList.getModFileById("ponder") != null;
    }

    @Override
    public String getRefMapperConfig() {
        return null;
    }

    @Override
    public boolean shouldApplyMixin(String targetClassName, String mixinClassName) {
        // Match based on simple naming or package conventions
        if (mixinClassName.contains("AppEngMixin")) {
            return isAE2Loaded;
        }
//        if (mixinClassName.contains("PonderMixin")) {
//            return isPonderLoaded;
//        }
        return true; // fallback: apply other mixins if added later
    }

    @Override
    public void acceptTargets(Set<String> myTargets, Set<String> otherTargets) {}

    @Override
    public List<String> getMixins() {
        return null; // Use mixins.json as source of mixin list
    }

    @Override
    public void preApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}

    @Override
    public void postApply(String targetClassName, ClassNode targetClass, String mixinClassName, IMixinInfo mixinInfo) {}
}
