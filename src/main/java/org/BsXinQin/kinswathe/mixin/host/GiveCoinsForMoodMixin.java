package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.BsXinQin.kinswathe.KinsWathe.NOELLESROLES_LOADED;

@Mixin(PlayerMoodComponent.class)
public abstract class GiveCoinsForMoodMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void GiveCoinsForMood(float mood, CallbackInfo ci) {
        if (NOELLESROLES_LOADED) return;
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        if (mood > getMood()) {
            if (gameWorld.getRole(player) != null) {
                if (gameWorld.getRole(player).getMoodType().equals(Role.MoodType.REAL)) {
                    PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
                    playerShop.addToBalance(50);
                }
            }
        }
    }
}