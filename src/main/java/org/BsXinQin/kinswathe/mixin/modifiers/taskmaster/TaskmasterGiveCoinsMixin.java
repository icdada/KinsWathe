package org.BsXinQin.kinswathe.mixin.modifiers.taskmaster;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerMoodComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Final;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(PlayerMoodComponent.class)
public abstract class TaskmasterGiveCoinsMixin {

    @Shadow public abstract float getMood();
    @Shadow @Final private PlayerEntity player;

    @Inject(method = "setMood", at = @At("HEAD"))
    void TaskmasterGiveCoins(float mood, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
        Role role = gameWorld.getRole((PlayerEntity) player);
        if (mood > getMood()) {
            if (gameWorld.getRole(player) != null) {
                WorldModifierComponent modifier = WorldModifierComponent.KEY.get(player.getWorld());
                if (modifier.isModifier(player, KinsWathe.TASKMASTER)) {
                    if (!gameWorld.isInnocent(player) &&
                        !KinsWathe.NEUTRAL_ROLES.contains(role) &&
                        !(KinsWathe.NOELLESROLES_LOADED && Noellesroles.KILLER_SIDED_NEUTRALS.contains(role))) {
                        playerShop.addToBalance(50);} else{
                        playerShop.addToBalance(25);
                    }
                }
            }
        }
    }
}