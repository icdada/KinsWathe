package org.BsXinQin.kinswathe.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameFunctions;
import net.minecraft.entity.player.PlayerEntity;
import net.minecraft.util.Identifier;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

import static org.BsXinQin.kinswathe.KinsWathe.NOELLESROLES_LOADED;

@Mixin(GameFunctions.class)
public class KillerMoneyIncreaseMixin {

    @Inject(method = "killPlayer(Lnet/minecraft/entity/player/PlayerEntity;ZLnet/minecraft/entity/player/PlayerEntity;Lnet/minecraft/util/Identifier;)V", at = @At("HEAD"))
    private static void KillerMoneyIncrease(PlayerEntity victim, boolean spawnBody, PlayerEntity player, Identifier identifier, CallbackInfo ci) {
        if (NOELLESROLES_LOADED) return;
        if (player != null) {
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(victim.getWorld());
            if (!gameWorld.isInnocent(player)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
                playerShop.addToBalance(100);
            }
        }
    }
}