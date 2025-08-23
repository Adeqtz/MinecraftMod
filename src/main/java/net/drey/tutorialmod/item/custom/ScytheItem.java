package net.drey.tutorialmod.item.custom;

import net.minecraft.block.Block;
import net.minecraft.block.Blocks;
import net.minecraft.block.CropBlock;
import net.minecraft.entity.EquipmentSlot;
import net.minecraft.item.Item;
import net.minecraft.item.ItemStack;
import net.minecraft.item.ItemUsageContext;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.world.World;
import net.minecraft.util.math.BlockPos;

public class ScytheItem extends Item {

    public ScytheItem(Settings settings) {
        super(settings);
    }

    @Override
    public ActionResult useOnBlock(ItemUsageContext context) {
        World world = context.getWorld();
        BlockPos pos = context.getBlockPos();

        if (!world.isClient()) {
            for (int dx = -1; dx <= 1; dx++) {
                for (int dz = -1; dz <= 1; dz++) {
                    BlockPos targetPos = pos.add(dx, 0, dz);
                    Block targetBlock = world.getBlockState(targetPos).getBlock();

                    if (targetBlock instanceof CropBlock crop) {
                        // Only harvest if fully grown
                        if (crop.isMature(world.getBlockState(targetPos))) {
                            // Drop items according to loot table
                            Block.dropStacks(world.getBlockState(targetPos), (ServerWorld) world, targetPos);

                            // Replant the crop (age 0) instead of deleting
                            world.setBlockState(targetPos, crop.withAge(0));
                            ItemStack itemStack = context.getStack();
                            itemStack.setDamage(itemStack.getDamage() + 1);
                            if (context.getPlayer() != null) {
                                context.getPlayer().setStackInHand(context.getHand(), itemStack);
                            }
                        }
                    }
                }
            }

            // Play sound
            world.playSound(null, pos, SoundEvents.BLOCK_CROP_BREAK, SoundCategory.BLOCKS, 1.0F, 1.0F);
        }

        return ActionResult.SUCCESS;

        // helps
    }
}
