package com.lithiumcraft.createresourcegeodes.loot;

import com.mojang.serialization.MapCodec;
import com.mojang.serialization.codecs.RecordCodecBuilder;
import it.unimi.dsi.fastutil.objects.ObjectArrayList;
import net.minecraft.core.registries.Registries;
import net.minecraft.resources.ResourceKey;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.storage.loot.LootContext;
import net.minecraft.world.level.storage.loot.LootTable;
import net.minecraft.world.level.storage.loot.predicates.LootItemCondition;
import net.neoforged.neoforge.common.loot.IGlobalLootModifier;
import net.neoforged.neoforge.common.loot.LootModifier;

public class InjectLootModifier extends LootModifier {

    public static final MapCodec<InjectLootModifier> CODEC = RecordCodecBuilder.mapCodec(inst ->
            LootModifier.codecStart(inst).and(inst.group(
                    ResourceKey.codec(Registries.LOOT_TABLE)
                            .fieldOf("loot_table")
                            .forGetter(m -> m.lootTable),
                    ResourceKey.codec(Registries.LOOT_TABLE)
                            .fieldOf("loot_table_to_inject_into")
                            .forGetter(m -> m.lootTableToInjectInto)
            )).apply(inst, InjectLootModifier::new)
    );

    private final ResourceKey<LootTable> lootTable;
    private final ResourceKey<LootTable> lootTableToInjectInto;

    public InjectLootModifier(LootItemCondition[] conditions,
                             ResourceKey<LootTable> lootTable,
                             ResourceKey<LootTable> lootTableToInjectInto) {
        super(conditions);
        this.lootTable = lootTable;
        this.lootTableToInjectInto = lootTableToInjectInto;
    }

    @SuppressWarnings({"deprecation", "java:S1874"})
    @Override
    protected ObjectArrayList<ItemStack> doApply(ObjectArrayList<ItemStack> generatedLoot, LootContext context) {
        // We intentionally use getRandomItemsRaw here (same reason as your other mod):
        // avoid re-entering NeoForge's loot modification pipeline and looping.
        context.getResolver().get(Registries.LOOT_TABLE, lootTable).ifPresent(extraTable -> {
            extraTable.value().getRandomItemsRaw(
                    context,
                    LootTable.createStackSplitter(context.getLevel(), generatedLoot::add)
            );
        });

        return generatedLoot;
    }

    @Override
    public MapCodec<? extends IGlobalLootModifier> codec() {
        return ModLootModifiers.INJECT_LOOT.get();
    }
}
