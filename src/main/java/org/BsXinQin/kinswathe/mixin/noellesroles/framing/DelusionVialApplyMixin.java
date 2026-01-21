package org.BsXinQin.kinswathe.mixin.noellesroles.framing;

import dev.doctor4t.wathe.block.FoodPlatterBlock;
import dev.doctor4t.wathe.block_entity.BeveragePlateBlockEntity;
import net.minecraft.block.BlockState;
import net.minecraft.block.entity.BlockEntity;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.sound.SoundCategory;
import net.minecraft.sound.SoundEvents;
import net.minecraft.util.ActionResult;
import net.minecraft.util.Hand;
import net.minecraft.util.hit.BlockHitResult;
import net.minecraft.util.math.BlockPos;
import net.minecraft.world.World;
import org.agmas.noellesroles.ModItems;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Unique;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import java.util.UUID;

import static org.BsXinQin.kinswathe.KinsWathe.NOELLESROLES_LOADED;

@Mixin(FoodPlatterBlock.class)
public abstract class DelusionVialApplyMixin {

    @Unique private static final UUID DELUSION_MARKER = UUID.fromString("00000000-0000-0000-dead-c0de00000000");

    @Inject(method = "onUse", at = @At("HEAD"), cancellable = true)
    private void DefenseVialApply(BlockState state, World world, BlockPos pos, PlayerEntity player, BlockHitResult hit, CallbackInfoReturnable<ActionResult> ci) {
        if (!NOELLESROLES_LOADED) return;
        if (world.isClient) return;
        if (player.isCreative()) return;
        BlockEntity platter = world.getBlockEntity(pos);
        if (platter instanceof BeveragePlateBlockEntity blockEntity) {
            if (player.getStackInHand(Hand.MAIN_HAND).isOf(ModItems.DELUSION_VIAL) && blockEntity.getPoisoner() == null) {
                blockEntity.setPoisoner(DELUSION_MARKER.toString());
                player.getStackInHand(Hand.MAIN_HAND).decrement(1);
                player.playSoundToPlayer(SoundEvents.BLOCK_BREWING_STAND_BREW, SoundCategory.BLOCKS, 0.5F, 1.0F);
                ci.setReturnValue(ActionResult.SUCCESS);
                ci.cancel();
            }
        }
    }
}