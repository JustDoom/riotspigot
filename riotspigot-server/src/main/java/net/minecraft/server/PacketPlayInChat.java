package net.minecraft.server;

import de.dytanic.$;
import de.dytanic.spigot.DytanicSpigot;

import java.io.IOException;

public class PacketPlayInChat implements Packet<PacketListenerPlayIn> {

    private String a;

    public PacketPlayInChat() {}

    public PacketPlayInChat(String s) {
        if (s.length() > 100) {
            s = s.substring(0, 100);
        }

        this.a = s;
    }

    public void a(PacketDataSerializer packetdataserializer) throws IOException {
        this.a = packetdataserializer.c(100);
    }

    public void b(PacketDataSerializer packetdataserializer) throws IOException {
        packetdataserializer.a(this.a);
    }

    // Spigot Start
    /* Dytanic edit remove ThreadPool, because is replaced with Dytanic's TaskScheduler
    private static final java.util.concurrent.ExecutorService executors = java.util.concurrent.Executors.newCachedThreadPool(
            new com.google.common.util.concurrent.ThreadFactoryBuilder().setDaemon( true ).setNameFormat( "Async Chat Thread - #%d" ).build() );
    */
    public void a(final PacketListenerPlayIn packetlistenerplayin) {
        if ( !a.startsWith($.SLASH_STRING) )
        {
            DytanicSpigot.getInstance().getTaskScheduler().schedule(new Runnable() //Dytanic edit replace with TaskScheduler
            {

                @Override
                public void run()
                {
                    packetlistenerplayin.a( PacketPlayInChat.this );
                }
            } );
            return;
        }
        // Spigot End
        packetlistenerplayin.a(this);
    }

    public String a() {
        return this.a;
    }
}
