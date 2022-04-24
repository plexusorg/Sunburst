package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.messaging.MessageData;
import dev.plex.player.ISunburstPlayer;
import dev.plex.util.ComponentUtil;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "message", description = "Sends a message to a player", usage = "/<command> <player> <message>", aliases = {"msg", "tell", "whisper"})
@CommandPerms(permission = "sunburst.command.message")
public class MessageCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length < 2)
        {
            return usage();
        }

        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            return confMsg("playerNotFound");
        }
        String message = StringUtils.join(args, " ", 1, args.length);
        MessageData data = new MessageData(sender instanceof ConsoleCommandSender ? null : player.getUniqueId(), target.getUniqueId(), message);
        if (sender instanceof Player)
        {
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
            sunburstPlayer.messageData(data);
        }
        ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(target.getUniqueId());
        sunburstPlayer.messageData(data);
        target.sendMessage(confMsg("messageReceived", sender.getName(), target.getName(), message));
        return confMsg("messageSent", sender.getName(), target.getName(), message);
    }
}
