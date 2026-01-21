package org.BsXinQin.kinswathe.mixin.roles.licensed_villain;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class LicensedVillainGiveCoinsMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void LicensedVillainGiveCoins(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorld = (GameWorldComponent)GameWorldComponent.KEY.get(player.getWorld());
        if (mood > getMood()) {
            if (gameWorld.getRole(player) != null) {
                if (gameWorld.isRole(player, KinsWathe.LICENSED_VILLAIN)) {
                    PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
                    playerShop.balance += 50;
                }
            }
        }
    }
}