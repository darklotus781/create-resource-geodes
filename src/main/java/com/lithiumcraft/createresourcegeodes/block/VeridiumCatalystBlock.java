package com.lithiumcraft.createresourcegeodes.block;

import com.lithiumcraft.createresourcegeodes.Config;
import net.minecraft.core.BlockPos;
import net.minecraft.resources.ResourceLocation;
import net.minecraft.util.Tuple;
import net.minecraft.world.InteractionHand;
import net.minecraft.world.InteractionResult;
import net.minecraft.world.entity.player.Player;
import net.minecraft.world.item.ItemStack;
import net.minecraft.world.level.Level;
import net.minecraft.world.level.LevelAccessor;
import net.minecraft.world.level.block.Block;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.BlockHitResult;
import net.minecraftforge.event.TickEvent;
import net.minecraftforge.eventbus.api.SubscribeEvent;
import net.minecraftforge.fml.common.Mod;
import net.minecraftforge.registries.ForgeRegistries;
import net.minecraftforge.registries.RegistryObject;

import java.util.ArrayList;
import java.util.Hashtable;
import java.util.Random;

import static net.minecraft.world.item.Items.REDSTONE;

@Mod.EventBusSubscriber
public class VeridiumCatalystBlock extends CatalystBlock {

    public static Block generatorBlock;
    private static final Random RAND = new Random();

    public VeridiumCatalystBlock(Properties pProperties) {
        super(pProperties);

        generatorBlock = RegistryObject.create(new ResourceLocation("create:veridium"), ForgeRegistries.BLOCKS).get();
    }

    protected static void generateBlockPlacements(Level level, BlockPos pos) {
        int sphereRadius = Config.veridiumCatalystRadius; // Adjust the sphere radius as needed
        int centerX = pos.getX(); // Center point x-coordinate
        int centerY = pos.getY(); // Center point y-coordinate
        int centerZ = pos.getZ(); // Center point z-coordinate
        double fillPercentage = Config.catalystFillPercentage;

        Random random = new Random();

        for (int i = centerX - sphereRadius; i <= centerX + sphereRadius; i++) {
            for (int j = centerY - sphereRadius; j <= centerY + sphereRadius; j++) {
                for (int k = centerZ - sphereRadius; k <= centerZ + sphereRadius; k++) {
                    // Calculate the distance from the center point
                    double distance = Math.sqrt(Math.pow(i - centerX, 2) + Math.pow(j - centerY, 2) + Math.pow(k - centerZ, 2));

                    // Check if the block is within the sphere
                    if (distance <= sphereRadius) {
                        // Generate a random value between 0 and 1
                        double randomValue = random.nextDouble();

                        // (i, j, k) are the coordinates of filled blocks
                        var newBlock = new BlockPos(i, j, k);

                        // Check if the random value is less than the fill percentage
                        if (randomValue < fillPercentage) {
                            // Ensure the new block coordinate is an air block.
                            if (!level.getBlockState(newBlock).toString().contains(AIR_BLOCK)) {
                                continue;
                            }

                            schedulePlacement(newBlock, level);
                        }
                    }
                }
            }
        }
    }

    public static final Hashtable<BlockPos, Tuple<Integer, LevelAccessor>> scheduled = new Hashtable<>();

    public static void schedulePlacement(BlockPos bp, LevelAccessor la) {
        if (scheduled.contains(bp)) return;

        scheduled.put(bp, new Tuple<>(RAND.nextInt(TICKS, TICKS * 2), la));
    }

    @SubscribeEvent
    public static void tickEvent(TickEvent.LevelTickEvent event) {
        if (event.phase == TickEvent.Phase.END && !event.level.isClientSide
                && event.level == event.level.getServer().overworld()) {
            ArrayList<BlockPos> Placed = new ArrayList<>();
            scheduled.forEach(((blockPos, tuple) -> {
                if (!event.level.equals(tuple.getB())) {
                    return;
                }
                if (tuple.getA() <= 0) {
                    Placed.add(blockPos);
                    tuple.getB().setBlock(blockPos, generatorBlock.defaultBlockState(), 3);
                    return;
                }
                tuple.setA(tuple.getA() - 1);
            }));
            Placed.forEach(scheduled::remove);
            Placed.clear();
        }
    }

    @Override
    public InteractionResult use(BlockState state, Level level, BlockPos pos, Player player, InteractionHand hand, BlockHitResult hit) {
        ItemStack held = player.getItemInHand(hand);

        if (state.getBlock() instanceof CatalystBlock) {
            if (held.getItem() == REDSTONE.asItem() && !level.isClientSide()) {
                if (!player.getAbilities().instabuild) {
                    held.shrink(1);
                }

                generateBlockPlacements(level, pos);

                return InteractionResult.SUCCESS;
            }
        }

        return super.use(state, level, pos, player, hand, hit);
    }
}
