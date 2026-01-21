package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.api.Role;
import dev.doctor4t.wathe.api.WatheRoles;
import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.Entity;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.noellesroles.Noellesroles;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

import static org.BsXinQin.kinswathe.KinsWathe.NOELLESROLES_LOADED;

@Mixin(WatheClient.class)
public abstract class InstinctMixin {

    @Shadow public static KeyBinding instinctKeybind;

    @Inject(method = "isInstinctEnabled", at = @At("HEAD"), cancellable = true)
    private static void Instinct(CallbackInfoReturnable<Boolean> ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.LICENSED_VILLAIN)) {
            if (instinctKeybind.isPressed()) {
                ci.setReturnValue(true);
                ci.cancel();
            }
        }
    }

    @Inject(method = "getInstinctHighlight", at = @At("HEAD"), cancellable = true)
    private static void InstinctColor(Entity target, CallbackInfoReturnable<Integer> ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        if (target instanceof PlayerEntity) {
            if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.LICENSED_VILLAIN) && WatheClient.isInstinctEnabled()) {
                ci.setReturnValue(KinsWathe.LICENSED_VILLAIN.color());
                ci.cancel();
            }
            if (!target.isSpectator() && WatheClient.isInstinctEnabled()) {
                Role role = gameWorld.getRole((PlayerEntity) target);
                if (role != null) {
                    if (WatheClient.isKiller() && WatheClient.isPlayerAliveAndInSurvival()) {
                        if (KinsWathe.NEUTRAL_ROLES.contains(role)) {
                            ci.setReturnValue(WatheRoles.CIVILIAN.color());
                            ci.cancel();
                        }
                        else if (NOELLESROLES_LOADED && Noellesroles.KILLER_SIDED_NEUTRALS.contains(role)) {
                            ci.setReturnValue(role.color());
                            ci.cancel();
                        }
                    }
                }
            }
        }
    }
}
