package com.lithiumcraft.createresourcegeodes.block;

import com.lithiumcraft.createresourcegeodes.item.ModItems;
import net.minecraft.core.BlockPos;
import net.minecraft.core.Direction;
import net.minecraft.core.particles.ParticleTypes;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.sounds.SoundEvents;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.Blocks;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.ModList;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import static net.minecraft.world.item.Items.REDSTONE;

@Mod.EventBusSubscriber
public class SkyStoneCatalystBlock extends CatalystBlock {

    public static Block generatorBlock;

    public static final Random RAND = new Random();


    public SkyStoneCatalystBlock(Properties pProperties) {
        super(pProperties);

        // Ensure AE2 is Optional, Sky Stone Catalyst doesn't spawn in the wild without AE2, so this will never be an issue.
        generatorBlock = !ModList.get().isLoaded("ae2") ?
            ModBlocks.FAUX_SKY_STONE.get() :
            RegistryObject.create(new ResourceLocation("ae2:sky_stone_block"), ForgeRegistries.BLOCKS).get();
    }

    protected static void generateBlockPlacements(Level level, BlockPos pos, int RADIUS, int CHANCE) {
        for (int x = -RADIUS; x <= RADIUS; x++) {
            for (int y = -RADIUS; y <= RADIUS; y++) {
                for (int z = -RADIUS; z <= RADIUS; z++) {
                    var temp = pos.offset(x, y, z);
                    int random = RAND.nextInt(100);
                    if (x == 0 && y == 0 && z == 0 || !level.getBlockState(temp).toString().contains("minecraft:air")) {
                        continue;
                    }
                    if (random <= CHANCE) {
                        schedulePlacement(temp, level);
                    }
                }
            }
        }
    }

    public static final Hashtable<BlockPos, Tuple<Integer, LevelAccessor>> schedule = new Hashtable<>();

    public static void schedulePlacement(BlockPos bp, LevelAccessor la) {
        if (schedule.contains(bp)) return;

        schedule.put(bp, new Tuple<>(RAND.nextInt(TICKS, TICKS * 2), la));
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide
                && event.level == event.level.getServer().overworld()) {
            ArrayList<BlockPos> removeMe = new ArrayList<>();
            schedule.forEach(((blockPos, tuple) -> {
                if (!event.level.equals(tuple.getB())) {
                    return;
                }
                if (tuple.getA() <= 0) {
                    removeMe.add(blockPos);
                    tuple.getB().setBlock(blockPos, generatorBlock.defaultBlockState(), 3);
                    return;
                }
                tuple.setA(tuple.getA() - 1);
            }));
            removeMe.forEach(schedule::remove);
            removeMe.clear();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);
        int RADIUS = RAND.nextInt(4) + 2;
        int CHANCE = 65 / RADIUS;

        if (held.getItem() == REDSTONE.asItem() && !level.isClientSide()) {
            if (!player.getAbilities().instabuild) {
                held.shrink(1);
            }

            generateBlockPlacements(level, pos, RADIUS, CHANCE);

            return InteractionResult.SUCCESS;
        } else if (held.getItem() == ModItems.ACTIVATOR_WAND.get()) {
            Direction direction = hit.getDirection().getOpposite();
            int x = pos.getX();
            int y = pos.getY();
            int z = pos.getZ();
            switch (direction.getName().toString()) {
                case "up":
                    ++y;
                    break;
                case "down":
                    --y;
                    break;
                case "east":
                    ++x;
                    break;
                case "west":
                    --x;
                    break;
                case "north":
                    --z;
                    break;
                case "south":
                    ++z;
                    break;
            }

            BlockPos newPos = new BlockPos(x, y, z);

            if (state.getBlock() instanceof CatalystBlock && held.getItem() == ModItems.ACTIVATOR_WAND.get()) {
                if (level.getBlockState(newPos).isAir() && y <= level.getMaxBuildHeight() - 10 && y >= level.getMinBuildHeight() + 10) {
                    if (!level.isClientSide()) {
                        level.setBlock(pos, Blocks.AIR.defaultBlockState(), Block.UPDATE_CLIENTS, 1);
                        level.setBlock(newPos, state.getBlock().defaultBlockState(), Block.UPDATE_CLIENTS, 1);
                    }

                    if (level.isClientSide()) {
                        for (int i = 0; i < 2; ++i) {
                            level.addParticle(ParticleTypes.PORTAL, pos.getX(), pos.getY(), pos.getZ(), (level.random.nextDouble() - 0.5D) * 2.0D, -level.random.nextDouble(), (level.random.nextDouble() - 0.5D) * 2.0D);
                        }
                        player.playSound(SoundEvents.ENDERMAN_TELEPORT);
                    }
                }
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
