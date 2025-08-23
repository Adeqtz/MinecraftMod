package net.drey.tutorialmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

// Add these imports:
import net.minecraft.item.Items;
import net.minecraft.block.Blocks;

public class ScytheItem extends Item {

    public ScytheItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();
        PlayerEntity player = context.getPlayer();

        if (world.isClient()) {
            return ActionResult.SUCCESS;
        }

        ServerWorld serverWorld = (ServerWorld) world;
        boolean harvestedAny = false;

        for (int dx = -1; dx <= 1; dx++) {
            for (int dz = -1; dz <= 1; dz++) {
                BlockPos targetPos = pos.add(dx, 0, dz);
                BlockState state = world.getBlockState(targetPos);
                Block block = state.getBlock();

                if (block instanceof CropBlock crop && crop.isMature(state)) {
                    ItemStack drop;

                    if (block == Blocks.WHEAT) {
                        drop = new ItemStack(Items.WHEAT);
                    } else if (block == Blocks.CARROTS) {
                        drop = new ItemStack(Items.CARROT);
                    } else if (block == Blocks.POTATOES) {
                        drop = new ItemStack(Items.POTATO);
                    } else if (block == Blocks.BEETROOTS) {
                        drop = new ItemStack(Items.BEETROOT);
                    } else {
                        drop = new ItemStack(crop.asItem());
                    }

                    Block.dropStack(serverWorld, targetPos, drop);
                    serverWorld.setBlockState(targetPos, crop.withAge(0));

                    harvestedAny = true;
                }
            }
        }

        if (harvestedAny) {
            world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        // Damage the scythe
        ItemStack itemStack = context.getStack();
        itemStack.setDamage(itemStack.getDamage() + 1);
        if (context.getPlayer() != null) {
            context.getPlayer().setStackInHand(context.getHand(), itemStack);
        }
        return ActionResult.SUCCESS;
    }
}