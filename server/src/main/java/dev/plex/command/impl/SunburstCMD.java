package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "sunburst", description = "View Sunburst information or reload configurations")
@CommandPerms(permission = "sunburst.command.sunburst")
public class SunburstCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length == 0)
        {
            return null;
        }
        if (args[0].equalsIgnoreCase("reload"))
        {
            plugin.getMessages().load();
            plugin.getConfiguration().load();
            return mini("<green>Reloaded all configurations.");
        }
        return null;
    }
}
