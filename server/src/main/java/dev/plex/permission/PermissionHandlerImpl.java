package dev.plex.permission;

import org.bukkit.command.CommandSender;

public class PermissionHandlerImpl implements IPermissionHandler
{
    @Override
    public boolean hasPermission(CommandSender sender, String permission)
    {
        return sender.hasPermission(permission);
    }

    @Override
    public void addPermission(CommandSender sender, String permission)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void removePermission(CommandSender sender, String permission)
    {
        throw new UnsupportedOperationException();
    }

    @Override
    public void setPermission(CommandSender sender, String permission, boolean value)
    {
        throw new UnsupportedOperationException();
    }
}
