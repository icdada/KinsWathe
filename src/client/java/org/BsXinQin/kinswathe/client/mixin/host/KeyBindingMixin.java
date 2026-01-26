package org.BsXinQin.kinswathe.client.mixin.host;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.client.WatheClient;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.option.KeyBinding;
import net.minecraft.entity.player.PlayerEntity;
import org.BsXinQin.kinswathe.KinsWathe;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfoReturnable;

@Mixin(KeyBinding.class)
public abstract class KeyBindingMixin {

    @Inject(method = "wasPressed", at = @At("HEAD"), cancellable = true)
    private void wasPressed(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            KeyBinding keyBinding = (KeyBinding) (Object) this;
            var options = MinecraftClient.getInstance().options;
            if (KinsWathe.EnableKeyNotInGame() && !gameWorld.isRunning()) {
                if (keyBinding == options.swapHandsKey ||
                    keyBinding == options.dropKey ||
                    keyBinding == options.advancementsKey) {
                    ci.setReturnValue(false);
                }
            } else {
                if (keyBinding == options.swapHandsKey ||
                    keyBinding == options.chatKey ||
                    keyBinding == options.commandKey ||
                    keyBinding == options.jumpKey ||
                    keyBinding == options.togglePerspectiveKey ||
                    keyBinding == options.dropKey ||
                    keyBinding == options.advancementsKey) {
                    ci.setReturnValue(false);
                }
            }
        }
    }

    @Inject(method = "isPressed", at = @At("HEAD"), cancellable = true)
    private void isPressed(CallbackInfoReturnable<Boolean> ci) {
        if (WatheClient.isPlayerAliveAndInSurvival()) {
            MinecraftClient client = MinecraftClient.getInstance();
            PlayerEntity player = client.player;
            GameWorldComponent gameWorld = GameWorldComponent.KEY.get(player.getWorld());
            KeyBinding keyBinding = (KeyBinding) (Object) this;
            var options = MinecraftClient.getInstance().options;
            if (KinsWathe.EnableKeyNotInGame() && !gameWorld.isRunning()) {
                if (keyBinding == options.swapHandsKey ||
                    keyBinding == options.dropKey ||
                    keyBinding == options.advancementsKey) {
                    ci.setReturnValue(false);
                }
            } else {
                if (keyBinding == options.swapHandsKey ||
                    keyBinding == options.chatKey ||
                    keyBinding == options.commandKey ||
                    keyBinding == options.jumpKey ||
                    keyBinding == options.togglePerspectiveKey ||
                    keyBinding == options.dropKey ||
                    keyBinding == options.advancementsKey) {
                    ci.setReturnValue(false);
                }
            }
        }
    }
}