package org.BsXinQin.kinswathe.client.mixin.roles.cleaner;

import dev.doctor4t.wathe.cca.GameWorldComponent;
import dev.doctor4t.wathe.cca.PlayerShopComponent;
import net.minecraft.client.MinecraftClient;
import net.minecraft.client.font.TextRenderer;
import net.minecraft.client.gui.DrawContext;
import net.minecraft.client.gui.hud.InGameHud;
import net.minecraft.client.render.RenderTickCounter;
import net.minecraft.text.Text;
import org.BsXinQin.kinswathe.KinsWathe;
import org.BsXinQin.kinswathe.component.AbilityPlayerComponent;
import org.BsXinQin.kinswathe.client.KinsWatheClient;
import org.spongepowered.asm.mixin.Mixin;
import org.spongepowered.asm.mixin.Shadow;
import org.spongepowered.asm.mixin.injection.At;
import org.spongepowered.asm.mixin.injection.Inject;
import org.spongepowered.asm.mixin.injection.callback.CallbackInfo;

@Mixin(InGameHud.class)
public abstract class CleanerHudMixin {

    @Shadow public abstract TextRenderer getTextRenderer();

    @Inject(method = "render", at = @At("TAIL"))
    public void CleanerHud(DrawContext context, RenderTickCounter tickCounter, CallbackInfo ci) {
        GameWorldComponent gameWorld = GameWorldComponent.KEY.get(MinecraftClient.getInstance().player.getWorld());
        AbilityPlayerComponent ability = AbilityPlayerComponent.KEY.get(MinecraftClient.getInstance().player);
        PlayerShopComponent playerShop = PlayerShopComponent.KEY.get(MinecraftClient.getInstance().player);
        if (gameWorld.isRole(MinecraftClient.getInstance().player, KinsWathe.CLEANER)) {
            int drawY = context.getScaledWindowHeight();

            Text line = Text.translatable("tip.kinswathe.ability_can_use", KinsWatheClient.abilityBind.getBoundKeyLocalizedText());

            if (playerShop.balance < 200) {
                line = Text.translatable("tip.kinswathe.cleaner.not_enough_money");
            }
            if (ability.cooldown > 0) {
                line = Text.translatable("tip.kinswathe.cooldown", ability.cooldown / 20);
            }

            drawY -= getTextRenderer().getWrappedLinesHeight(line, 999999);
            context.drawTextWithShadow(getTextRenderer(), line, context.getScaledWindowWidth() - getTextRenderer().getWidth(line), drawY, KinsWathe.CLEANER.color());
        }
    }
}