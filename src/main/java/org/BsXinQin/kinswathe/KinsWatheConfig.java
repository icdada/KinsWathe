package org.BsXinQin.kinswathe;

import dev.doctor4t.wathe.game.GameConstants;
import dev.isxander.yacl3.config.v2.api.ConfigClassHandler;
import dev.isxander.yacl3.config.v2.api.SerialEntry;
import dev.isxander.yacl3.config.v2.api.serializer.GsonConfigSerializerBuilder;
import net.fabricmc.loader.api.FabricLoader;
import net.minecraft.util.Identifier;

public class KinsWatheConfig {
    /// 配置文件设置
    public static ConfigClassHandler<KinsWatheConfig> HANDLER = ConfigClassHandler.createBuilder(KinsWatheConfig.class).id(Identifier.of(KinsWathe.MOD_ID, "config")).serializer(config -> GsonConfigSerializerBuilder.create(config).setPath(FabricLoader.getInstance().getConfigDir().resolve(KinsWathe.MOD_ID + ".json5")).setJson5(true).build()).build();

    @SerialEntry(comment = "Starting cooldown.")
    public int StartingCooldown = GameConstants.getInTicks(0,30) / 20;

    @SerialEntry(comment = "Whether to enable staminabar.")
    public static boolean EnableStaminaBar = true;

    /// 关于NoellesRoles修改
    @SerialEntry(comment = "\n\n[Noelle's Roles] Modify:\nWhether to enable Noelle's Roles modify.")
    public static boolean NoellesRolesModify = true;

    @SerialEntry(comment = "Bartender: modify the price of defense_vial in bartender's shop.")
    public static int BartenderPriceModify = 150;

    @SerialEntry(comment = "Recaller: whether to enable the special effects of recaller's ability.")
    public static boolean RecallerAbilityModify = true;
}