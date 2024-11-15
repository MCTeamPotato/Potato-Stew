package com.teampotato.potatostew.mixin.helpfulhitboxes;

import net.minecraft.core.BlockPos;
import net.minecraft.world.level.BlockGetter;
import net.minecraft.world.level.block.CrossCollisionBlock;
import net.minecraft.world.level.block.state.BlockState;
import net.minecraft.world.phys.shapes.CollisionContext;
import net.minecraft.world.phys.shapes.Shapes;
import net.minecraft.world.phys.shapes.VoxelShape;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

// https://github.com/flowerfugue/HelpfulHitboxes-Forge/blob/main/src/main/java/com/github/abigailfails/helpfulhitboxes/mixin/FourWayBlockMixin.java
@Mixin(CrossCollisionBlock.class)
public class CrossCollisionBlockMixin {
    @Inject(method = "getShape", at = @At("HEAD"), cancellable = true)
    protected void checkHeldBlock(BlockState blockState, BlockGetter blockGetter, BlockPos blockPos, CollisionContext context, CallbackInfoReturnable<VoxelShape> cir) {
        if (context.isHoldingItem(blockState.getBlock().asItem())) {
            cir.setReturnValue(Shapes.block());
        }
    }
}
