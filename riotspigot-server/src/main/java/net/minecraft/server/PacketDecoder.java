package net.minecraft.server;

import de.dytanic.$;
import de.dytanic.log.DytanicLogger;
import io.netty.buffer.ByteBuf;
import io.netty.channel.ChannelHandlerContext;
import io.netty.handler.codec.ByteToMessageDecoder;
import java.io.IOException;
import java.util.List;

public class PacketDecoder extends ByteToMessageDecoder {

    private static final DytanicLogger a = DytanicLogger.getInstance();
    //private static final Marker b = MarkerManager.getMarker("PACKET_RECEIVED", NetworkManager.b); Dytanic
    private final EnumProtocolDirection c;

    public PacketDecoder(EnumProtocolDirection enumprotocoldirection) {
        this.c = enumprotocoldirection;
    }

    protected void decode(ChannelHandlerContext channelhandlercontext, ByteBuf bytebuf, List<Object> list) throws Exception {
        if (bytebuf.readableBytes() != 0) {
            PacketDataSerializer packetdataserializer = new PacketDataSerializer(bytebuf);
            int i = packetdataserializer.e();
            Packet packet = ((EnumProtocol) channelhandlercontext.channel().attr(NetworkManager.c).get()).a(this.c, i);

            if (packet == null) {
                throw new IOException("Bad packet id " + i);
            } else {
                packet.a(packetdataserializer);
                if (packetdataserializer.readableBytes() > 0) {
                    throw new IOException("Packet " + ((EnumProtocol) channelhandlercontext.channel().attr(NetworkManager.c).get()).a() + $.SLASH_STRING + i + " (" + packet.getClass().getSimpleName() + ") was larger than I expected, found " + packetdataserializer.readableBytes() + " bytes extra whilst reading packet " + i);
                } else {
                    list.add(packet);
                    if (PacketDecoder.a.isDebugEnabled()) {
                        PacketDecoder.a.debug( " IN: [{}:{}] {}", new Object[] { channelhandlercontext.channel().attr(NetworkManager.c).get(), Integer.valueOf(i), packet.getClass().getName()});
                    }

                }
            }
        }
    }
}
