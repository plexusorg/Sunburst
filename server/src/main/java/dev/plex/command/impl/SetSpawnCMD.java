package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.command.util.RequiredSource;
import dev.plex.util.XYZLocation;
import dev.plex.world.WorldSettingsImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.command.CommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "setspawn", description = "Sets the world spawn")
@CommandPerms(permission = "sunburst.command.setspawn", source = RequiredSource.PLAYER)
public class SetSpawnCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        XYZLocation location = new XYZLocation(player.getLocation().getX(), player.getLocation().getY(), player.getLocation().getZ(), player.getLocation().getYaw(), player.getLocation().getPitch());
        if (plugin.getWorldManager().getSettings(player.getWorld()) != null)
        {
            plugin.getWorldManager().getSettings(player.getWorld()).spawnLocation(location);
        }
        else
        {
            WorldSettingsImpl worldSettings = new WorldSettingsImpl();
            worldSettings.world(player.getWorld());
            worldSettings.spawnLocation(location);
            plugin.getWorldManager().addSetting(worldSettings);
        }
        plugin.getWorldManager().writeSettings();
        return confMsg("spawnSet");
    }
}
