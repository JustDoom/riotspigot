package net.minecraft.server;

import de.dytanic.log.DytanicLogger;

import java.util.concurrent.ExecutionException;
import java.util.concurrent.FutureTask;

public class SystemUtils {

    public static <V> V a(FutureTask<V> var0) {

        try {
            var0.run();
            return var0.get();
        } catch (ExecutionException | InterruptedException var3) {
            DytanicLogger.getInstance().fatal("Error executing task", var3);
        }

        return null;
    }
}
