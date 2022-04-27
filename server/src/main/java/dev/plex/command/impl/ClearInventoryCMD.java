package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "clearinventory", description = "Clears your inventory, or optionally a different player", usage = "/<command> [player]", aliases = {"clear", "ci", "clearinv", "clean"})
@CommandPerms(permission = "sunburst.command.clearinventory")
public class ClearInventoryCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length == 0)
        {
            if (sender instanceof ConsoleCommandSender)
            {
                return usage();
            }
            if (player != null)
            {
                player.getInventory().clear();
            }
            return confMsg("clearedInventory");
        }
        return null;
    }
}
