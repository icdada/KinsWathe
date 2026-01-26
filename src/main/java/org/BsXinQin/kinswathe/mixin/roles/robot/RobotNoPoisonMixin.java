package org.BsXinQin.kinswathe.mixin.roles.robot;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerPoisonComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerPoisonComponent.class)
public class RobotNoPoisonMixin {

    @Shadow @Final private PlayerEntity player;

    @Inject(method = "setPoisonTicks", at = @At("HEAD"), cancellable = true)
    private void RobotNoPoison(int ticks, java.util.UUID poisoner, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(this.player.getWorld());
        if (gameWorld.isRole(this.player, KinsWathe.ROBOT)) {
            ci.cancel();
        }
    }
}