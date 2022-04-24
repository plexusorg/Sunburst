package dev.plex.permission;

import org.bukkit.command.CommandSender;

public interface IPermissionHandler
{
    boolean hasPermission(CommandSender sender, String permission);

    void addPermission(CommandSender sender, String permission);

    void removePermission(CommandSender sender, String permission);

    void setPermission(CommandSender sender, String permission, boolean value);
}
