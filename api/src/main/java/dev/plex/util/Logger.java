package dev.plex.util;

import dev.plex.plugin.SunburstPlugin;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;

public class Logger
{

    public static void log(String message)
    {
        Bukkit.getScheduler().runTask(SunburstPlugin.getPlugin(), () -> Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<green>[Sunburst] <gold>" + message)));
    }

    public static void error(String message)
    {
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<red>[Sunburst Error] <gold>" + message));
    }

    public static void warn(String message)
    {
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<#db6b0f>[Sunburst Warning] <gold>" + message));
    }

    public static void debug(Object message)
    {
        Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().deserialize("<dark_purple>[Sunburst Debug] <gold>" + message));
    }
}
