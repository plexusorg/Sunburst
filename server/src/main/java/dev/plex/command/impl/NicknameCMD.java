package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.player.ISunburstPlayer;
import dev.plex.util.ComponentUtil;
import dev.plex.util.Logger;
import dev.plex.util.MojangUtils;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.apache.commons.lang.StringUtils;
import org.bukkit.Bukkit;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "nickname", description = "Manages your chat display name / nick name", usage = "/<command> [player] [new nickname | off]", aliases = {"nick"})
@CommandPerms(permission = "sunburst.command.nickname")
public class NicknameCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length == 0)
        {
            if (sender instanceof ConsoleCommandSender)
            {
                return confMsg("commandCheckConsole", this.getUsage());
            }
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
            if (sunburstPlayer.displayName() == null)
            {
                return confMsg("nicknameNotFound");
            }
            return confMsg("nickname", ComponentUtil.REGULAR_TAGS, ComponentUtil.mmCustom(sunburstPlayer.displayName(), ComponentUtil.REGULAR_TAGS));
        }
        if (Bukkit.getOnlinePlayers().stream().anyMatch(p -> p.getName().equalsIgnoreCase(args[0])))
        {
            Player target = getNonNullPlayer(args[0]);
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(target.getUniqueId());
            if (args.length < 2)
            {
                return usage();
            }
            if (args[1].equalsIgnoreCase("off"))
            {
                sunburstPlayer.displayName(null);
                target.sendMessage(confMsg("nicknameRemoved"));
                return confMsg("nicknameRemovedOther", target.getName());
            }
            String nickname = StringUtils.join(args, " ", 1, args.length);
            nickname = nickname.replace("<newline>", "").replace("<br>", "");
            Component newNickname = MiniMessage.miniMessage().deserialize(nickname, ComponentUtil.REGULAR_TAGS);
            String rawNick = PlainTextComponentSerializer.plainText().serialize(newNickname);
            if (!rawNick.equalsIgnoreCase(target.getName()) && Bukkit.getOfflinePlayer(rawNick).hasPlayedBefore())
            {
                if (Bukkit.getOfflinePlayer(nickname).hasPlayedBefore())
                {
                    return confMsg("nicknameMatchesPlayer");
                }
            }
            sunburstPlayer.displayName(newNickname);
            target.sendMessage(confMsg("nicknameSet", ComponentUtil.REGULAR_TAGS, ComponentUtil.mmCustom(newNickname, ComponentUtil.REGULAR_TAGS)));
            return confMsg("nicknameSetOther", ComponentUtil.REGULAR_TAGS, target.getName(), ComponentUtil.mmCustom(newNickname, ComponentUtil.REGULAR_TAGS));
        }

        if (args[0].equalsIgnoreCase("off"))
        {
            if (sender instanceof ConsoleCommandSender)
            {
                return confMsg("commandCheckConsole", this.getUsage());
            }
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
            sunburstPlayer.displayName(null);
            return confMsg("nicknameRemoved");
        }
        if (sender instanceof ConsoleCommandSender)
        {
            return confMsg("commandCheckConsole", this.getUsage());
        }
        ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
        String nickname = StringUtils.join(args, " ");
        nickname = nickname.replace("<newline>", "").replace("<br>", "");
        Component newNickname = MiniMessage.miniMessage().deserialize(nickname, ComponentUtil.REGULAR_TAGS);
        String rawNick = PlainTextComponentSerializer.plainText().serialize(newNickname);
        if (!rawNick.equalsIgnoreCase(player.getName()) && MojangUtils.getInfo(rawNick) != null)
        {
            return confMsg("nicknameMatchesPlayer");
        }
        sunburstPlayer.displayName(newNickname);
        return confMsg("nicknameSet", ComponentUtil.REGULAR_TAGS, ComponentUtil.mmCustom(newNickname, ComponentUtil.REGULAR_TAGS));
    }
}
