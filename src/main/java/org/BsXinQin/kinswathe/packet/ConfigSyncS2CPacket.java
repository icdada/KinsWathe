package org.BsXinQin.kinswathe.packet;

import net.minecraft.network.RegistryByteBuf;
import net.minecraft.network.codec.PacketCodec;
import net.minecraft.network.codec.PacketCodecs;
import net.minecraft.network.packet.CustomPayload;
import org.BsXinQin.kinswathe.KinsWathe;

import java.util.HashMap;
import java.util.Map;

public record ConfigSyncS2CPacket(Map<String, Integer> intConfigs, Map<String, Boolean> boolConfigs) implements CustomPayload {

    public static final CustomPayload.Id<ConfigSyncS2CPacket> ID =
            new CustomPayload.Id<>(KinsWathe.id("config_sync"));

    public static final PacketCodec<RegistryByteBuf, ConfigSyncS2CPacket> CODEC =
            PacketCodec.tuple(
                    PacketCodecs.map(HashMap::new, PacketCodecs.STRING, PacketCodecs.INTEGER),
                    ConfigSyncS2CPacket::intConfigs,
                    PacketCodecs.map(HashMap::new, PacketCodecs.STRING, PacketCodecs.BOOL),
                    ConfigSyncS2CPacket::boolConfigs,
                    ConfigSyncS2CPacket::new
            );

    @Override
    public Id<? extends CustomPayload> getId() {
        return ID;
    }
}