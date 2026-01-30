package org.BsXinQin.kinswathe.mixin.modifiers.magnate;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import dev.doctor4t.wathe.game.GameConstants;
import dev.doctor4t.wathe.game.gamemode.MurderGameMode;
import net.minecraft.server.network.ServerPlayerEntity;
import net.minecraft.server.world.ServerWorld;
import org.BsXinQin.kinswathe.KinsWathe;
import org.agmas.harpymodloader.component.WorldModifierComponent;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(MurderGameMode.class)
public abstract class MagnatePassiveIncomeMixin {

    @Inject(method = "tickServerGameLoop", at = @At("TAIL"))
    public void MagnatePassiveIncome(ServerWorld serverWorld, GameWorldComponent gameWorld, CallbackInfo ci) {
        if (gameWorld.getGameStatus() != GameWorldComponent.GameStatus.ACTIVE) {return;}
        for (ServerPlayerEntity player : serverWorld.getPlayers()) {
            WorldModifierComponent modifier = WorldModifierComponent.KEY.get(serverWorld);
            if (modifier.isModifier(player, KinsWathe.MAGNATE)) {
                PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(player);
                Integer passiveIncome = GameConstants.PASSIVE_MONEY_TICKER.apply(serverWorld.getTime());
                if (passiveIncome > 0) {
                    playerShop.addToBalance(passiveIncome);
                }
            }
        }
    }
}