package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.command.util.RequiredSource;
import dev.plex.messaging.MessageData;
import dev.plex.player.ISunburstPlayer;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.BooleanUtils;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@CommandInfo(name = "god", description = "Toggles god mode", usage = "/<command> [player]", aliases = {"godmode"})
@CommandPerms(permission = "sunburst.command.god")
public class GodCMD extends SunburstCommand
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
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
            sunburstPlayer.godMode(!sunburstPlayer.godMode());
            return confMsg("godModeToggle", BooleanUtils.toStringOnOff(sunburstPlayer.godMode()));
        }
        return null;
    }
}
