package net.drey.tutorialmod.block.custom;

import net.drey.tutorialmod.item.ModItems;
import net.minecraft.block.Block;
import net.minecraft.block.BlockState;
import net.minecraft.entity.Entity;
import net.minecraft.entity.ItemEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.entity.projectile.ProjectileEntity;
import net.minecraft.item.ItemStack;
import net.minecraft.item.Items;
import net.minecraft.particle.ParticleTypes;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;

public class MagicBlock  extends Block {
    public MagicBlock(Settings settings) {
        super(settings);
    }

    @Override
    protected ActionResult onUse(BlockState state, World world, BlockPos pos, PlayerEntity player,
                                 BlockHitResult hit) {
        world.playSound(player, pos, SoundEvents.BLOCK_AMETHYST_BLOCK_CHIME, SoundCategory.BLOCKS, 1F, 1F);

        return ActionResult.SUCCESS;
    }

    public void onProjectileHit(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, ProjectileEntity projectile, Entity entity) {
        if(projectile instanceof ProjectileEntity arrow) {

        }
    }

    @Override
    public void onSteppedOn(World world, BlockPos pos, BlockState state, Entity entity) {
        if(entity instanceof ItemEntity itemEntity) {
            if(itemEntity.getStack().getItem() == ModItems.RAW_PINK_GARNET) {
                for (int i = 0; i < 10; i++) {
                    world.addParticle(
                            ParticleTypes.FIREWORK,
                            pos.getX() + 0.5,
                            pos.getY() + ((1.2 + i) - 0.8),
                            pos.getZ() + 0.5,
                            0, 0.1, 0
                    );
                }
                itemEntity.setStack(new ItemStack(Items.DIAMOND, itemEntity.getStack().getCount()));
            }
        }


        super.onSteppedOn(world, pos, state, entity);
    }
}
