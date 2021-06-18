package de.dytanic.spigot;

import net.minecraft.server.Chunk;
import net.minecraft.server.ChunkProviderServer;

import java.util.concurrent.Callable;
import java.util.function.Consumer;

/**
 * Created by Tareko on 26.01.2018.
 */
public class ChunkCallable implements Callable<Chunk> {

    private ChunkProviderServer chunkProvider;

    private int x, z;

    private Consumer<Chunk> callback;

    public ChunkCallable(ChunkProviderServer chunkProvider, int x, int z)
    {
        this.chunkProvider = chunkProvider;
        this.x = x;
        this.z = z;
    }

    public ChunkCallable(ChunkProviderServer chunkProvider, int x, int z, Consumer<Chunk> callback)
    {
        this.chunkProvider = chunkProvider;
        this.x = x;
        this.z = z;
        this.callback = callback;
    }

    public ChunkCallable(ChunkProviderServer chunkProvider, int x, int z, final Runnable callback$)
    {
        this.chunkProvider = chunkProvider;
        this.x = x;
        this.z = z;
        this.callback = new Consumer<Chunk>() {
            @Override
            public void accept(Chunk chunk)
            {
                if(callback$ != null)
                callback$.run();
            }
        };
    }

    private Chunk load()
    {
        Chunk chunk = this.chunkProvider.getChunkAt(this.x, this.z);

        if(chunk != null && callback != null) callback.accept(chunk);

        return chunk;
    }

    @Override
    public Chunk call() throws Exception
    {
        return load();
    }
}