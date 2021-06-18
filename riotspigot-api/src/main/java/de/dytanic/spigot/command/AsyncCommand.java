package de.dytanic.spigot.command;

import org.bukkit.Bukkit;

/**
 * This class make, for mark a command for async tasking
 */
public interface AsyncCommand {

    default void synchronize(Runnable runnable)
    {
        Bukkit.dytanicServer().runTask(runnable);
    }

}