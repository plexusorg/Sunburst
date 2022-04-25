package dev.plex.command.impl;

import dev.plex.command.SunburstCommand;
import dev.plex.command.util.CommandInfo;
import dev.plex.command.util.CommandPerms;
import dev.plex.command.util.RequiredSource;
import dev.plex.exception.WorldNotFoundException;
import dev.plex.util.Logger;
import dev.plex.world.WorldSettingsImpl;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.command.CommandSender;
import org.bukkit.command.ConsoleCommandSender;
import org.bukkit.entity.Player;
import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

@CommandInfo(name = "spawn", description = "Teleports to spawn", usage = "/<command> [player]")
@CommandPerms(permission = "sunburst.command.spawn", source = RequiredSource.BOTH)
public class SpawnCMD extends SunburstCommand
{
    @Override
    public Component execute(@NotNull CommandSender sender, @Nullable Player player, String[] args)
    {
        if (args.length == 0)
        {
            if (sender instanceof ConsoleCommandSender)
            {
                return usage();
            } else
            {
                String globalSpawn = plugin.getConfiguration().getString("options.global-spawn-world", "");
                if (!globalSpawn.isEmpty())
                {
                    World world = Bukkit.getWorld(globalSpawn);
                    if (world == null)
                    {
                        throw new WorldNotFoundException();
                    }
                    WorldSettingsImpl worldSettings = plugin.getWorldManager().getSettings(world);
                    if (worldSettings == null)
                    {
                        Logger.warn("The world '" + globalSpawn + "' does not have a set spawn point! Defaulting to bukkit's methods");
                        player.teleportAsync(world.getSpawnLocation());
                    } else
                    {
                        Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
                        player.teleportAsync(loc);
                    }
                }
                WorldSettingsImpl worldSettings = plugin.getWorldManager().getSettings(player.getWorld());
                if (worldSettings != null)
                {
                    Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
                    player.teleportAsync(loc);
                }
            }
            return null;
        }
        if (!sender.hasPermission(getPermission() + ".other"))
        {
            return confMsg("noPermission", getPermission() + ".other");
        }
        Player target = Bukkit.getPlayer(args[0]);
        if (target == null)
        {
            return confMsg("playerNotFound");
        }
        String globalSpawn = plugin.getConfiguration().getString("options.global-spawn-world", "");
        if (!globalSpawn.isEmpty())
        {
            World world = Bukkit.getWorld(globalSpawn);
            if (world == null)
            {
                throw new WorldNotFoundException();
            }
            WorldSettingsImpl worldSettings = plugin.getWorldManager().getSettings(world);
            if (worldSettings == null)
            {
                Logger.warn("The world '" + globalSpawn + "' does not have a set spawn point! Defaulting to bukkit's methods");
                target.teleportAsync(world.getSpawnLocation());
            } else
            {
                Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
                target.teleportAsync(loc);
            }
        }
        WorldSettingsImpl worldSettings = plugin.getWorldManager().getSettings(target.getWorld());
        if (worldSettings != null)
        {
            Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
            target.teleportAsync(loc);
        }
        return null;
    }
}
