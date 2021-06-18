package org.spigotmc;

public class AsyncCatcher
{

    public static boolean enabled = /*true; dytanic replace with false */ false;

    public static void catchOp(String reason)
    {
        //Dytanic start
        /*
        if ( enabled && Thread.currentThread() != MinecraftServer.getServer().primaryThread )
        {
            throw new IllegalStateException( "Asynchronous " + reason + "!" );
        }
        */
        //Dytanic end
    }
}
