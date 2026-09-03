package org.digitalpear.manaful.common;

import net.minecraft.network.FriendlyByteBuf;
import net.minecraft.network.PacketListener;
import net.minecraft.network.codec.ByteBufCodecs;
import net.minecraft.network.codec.StreamCodec;
import net.minecraft.network.protocol.Packet;
import net.minecraft.network.protocol.common.custom.CustomPacketPayload;
import net.minecraft.resources.Identifier;
import org.digitalpear.manaful.Manaful;

public record ManaChangePacket(double newAmount) implements CustomPacketPayload {
    public static final Identifier ID = Manaful.id("mana_change");
    public static final Type<ManaChangePacket> TYPE = new Type<>(ID);
    public static final StreamCodec<FriendlyByteBuf, ManaChangePacket> CODEC =
            StreamCodec.composite(
                    ByteBufCodecs.DOUBLE,
                    ManaChangePacket::newAmount,
                    ManaChangePacket::new
            );
    @Override
    public Type<? extends CustomPacketPayload> type() {
        return TYPE;
    }
}
