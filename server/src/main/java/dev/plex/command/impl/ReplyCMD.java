package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.command.util.RequiredSource;
import dev.plex.messaging.MessageData;
import dev.plex.player.ISunburstPlayer;
import net.kyori.adventure.text.Component;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.UUID;

@CommandInfo(name = "reply", description = "Replies to a player", usage = "/<command> <message>", aliases = {"r", "respond"})
@CommandPerms(permission = "sunburst.command.reply", source = RequiredSource.PLAYER)
public class ReplyCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length < 1)
        {
            return usage();
        }
        ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());

        String message = StringUtils.join(args, " ");
        MessageData data = sunburstPlayer.messageData();
        if (data == null)
        {
            return confMsg("messengerNotFound");
        }
        UUID uuid = data.getSender() != null && data.getSender().equals(player.getUniqueId()) ? data.getReceiver() : data.getSender();
        CommandSender target;
        if (uuid == null)
        {
            target = Bukkit.getConsoleSender();
            Bukkit.getConsoleSender().sendMessage(confMsg("messageReceived", target.getName(), "CONSOLE", message));
        } else
        {
            target = Bukkit.getPlayer(uuid);
            if (target == null)
            {
                return confMsg("playerNotFound");
            }
            target.sendMessage(confMsg("messageReceived", sender.getName(), target.getName(), message));
        }
        return confMsg("messageSent", sender.getName(), target.getName(), message);
    }
}
