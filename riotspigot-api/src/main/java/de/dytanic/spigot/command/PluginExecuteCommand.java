package de.dytanic.spigot.command;

import de.dytanic.$;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;
import org.bukkit.command.CommandSender;
import org.bukkit.plugin.InvalidDescriptionException;
import org.bukkit.plugin.InvalidPluginException;
import org.bukkit.plugin.Plugin;

import java.io.*;
import java.nio.file.Files;
import java.nio.file.Paths;
import java.util.Arrays;

/**
 * Created by Tareko on 19.01.2018.
 */
public final class PluginExecuteCommand extends Command {

    public PluginExecuteCommand() {
        super("plugin");
        setAliases(Arrays.asList("pl", "plugins"));

        setPermission("dyspigot.command.plugin");
    }

    @Override
    public boolean execute(CommandSender sender, String commandLabel, String[] args) {
        if (!testPermission(sender)) return false;

        if (args.length == 2) {
            switch (args[0].toLowerCase()) {
                case "enable": {

                    if (Bukkit.getPluginManager().isPluginEnabled(args[1])) {
                        sender.sendMessage("§8[§a§lSpigot§8] §cThe plugin is already loaded.");
                        return false;
                    }

                    if (Files.exists(Paths.get("plugins/" + args[1] + ".jar"))) {
                        try {
                            Bukkit.getPluginManager().enablePlugin(
                                Bukkit.getPluginManager().loadPlugin(new File("plugins/" + args[1] + ".jar"))
                            );
                            sender.sendMessage("§8[§a§lSpigot§8] §7Plugin is successfully loaded and enabled");
                        } catch (InvalidPluginException | InvalidDescriptionException e) {
                            e.printStackTrace();

                            try (StringWriter stringWriter = new StringWriter();
                                 PrintWriter printWriter = new
                                     PrintWriter(stringWriter)) {
                                e.printStackTrace(printWriter);
                                sender.sendMessage(
                                    "§8[§a§lSpigot§8] §cAn error at execute this command [" + stringWriter + "]");
                            } catch (IOException e1) {
                                e1.printStackTrace();
                            }
                        }
                    } else sender.sendMessage(
                        "§8[§a§lSpigot§8] §cThe Plugin cannot be enable, because we can't found the plugin file \""
                            + args[1]
                            + ".jar\"!");
                }
                break;
                case "disable": {
                    if (!Bukkit.getPluginManager().isPluginEnabled(args[1])) {
                        sender.sendMessage("§8[§a§lSpigot§8] §cThe plugin is currently disabled or not loaded!");
                        return false;
                    }

                    Bukkit.getPluginManager().removePlugin(Bukkit.getPluginManager().getPlugin(args[1]));
                    sender.sendMessage("§8[§a§lSpigot§8] §7The plugin is now disabled and removed from the cache!");
                }
                break;
            }
        } else {
            StringBuilder stringBuilder = new StringBuilder();

            for (Plugin plugin : Bukkit.getPluginManager().getPlugins())
                stringBuilder.append(plugin.isEnabled() ? "§a" : "§c")
                    .append(plugin.getName())
                    .append(",");

            sender.sendMessage(
                $.SPACE_STRING,
                "§8[§a§lSpigot§8] §7/plugin enable <plugin>",
                "§8[§a§lSpigot§8] §7/plugin disable <plugin>",
                $.SPACE_STRING,
                "§8[§a§lSpigot§8] §7Plugins §a" + Bukkit.getPluginManager().getPlugins().length + " §8» §7"
                    + stringBuilder
            );
        }

        return true;
    }

}