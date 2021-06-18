package org.spigotmc;

import de.dytanic.spigot.command.AsyncCommand;
import net.minecraft.server.MinecraftServer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.ChatColor;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;

import javax.management.MBeanServerConnection;
import java.io.IOException;
import java.lang.management.ManagementFactory;
import java.lang.management.OperatingSystemMXBean;
import java.lang.management.RuntimeMXBean;

public class TicksPerSecondCommand extends Command implements AsyncCommand {

    public TicksPerSecondCommand(String name) {
        super(name);
        this.description = "Gets the current ticks per second for the server";
        this.usageMessage = "/tps";
        this.setPermission("bukkit.command.tps");
    }

    @Override
    public boolean execute(CommandSender sender, String currentAlias, String[] args) {
        if (!testPermission(sender)) {
            return true;
        }

        double[] tps = org.bukkit.Bukkit.spigot().getTPS();
        String[] tpsAvg = new String[tps.length];

        for (int i = 0; i < tps.length; i++) {
            tpsAvg[i] = format(tps[i]);
        }

        sender.sendMessage(
            String.format("§8[§a§lSpigot§8] §7Current §8» %s", format(MinecraftServer.getServer().currentTps)));
        sender.sendMessage(
            String.format("§8[§a§lSpigot§8] §7Last 1m, 5m, 15m §8» %s",
                          StringUtils.join(tpsAvg, ", ")));

        return true;
    }

    private static String format(double tps)
    {
        return ((tps > 36.0) ? ChatColor.GREEN : (tps > 32.0) ? ChatColor.YELLOW : ChatColor.RED)
            + ((tps > 40.0) ? "*" : "") + Math.min(Math.round(tps * 100.0) / 100.0, 40.0);
    }
}
