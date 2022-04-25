package dev.plex.listener.impl.player;

import dev.plex.exception.WorldNotFoundException;
import dev.plex.listener.SunburstListener;
import dev.plex.util.Logger;
import dev.plex.world.WorldSettingsImpl;
import org.bukkit.Bukkit;
import org.bukkit.Location;
import org.bukkit.World;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;
import org.spigotmc.event.player.PlayerSpawnLocationEvent;

public class SpawnListener extends SunburstListener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onJoin(PlayerSpawnLocationEvent event)
    {
        if (!plugin.getConfiguration().getBoolean("options.auto-spawn"))
        {
            return;
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
                event.setSpawnLocation(world.getSpawnLocation());
            } else
            {
                Logger.log("Teleporting to: " + worldSettings.toJSON());
                Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
                Logger.log("Location: " + loc);
                event.getPlayer().setBedSpawnLocation(loc);
                event.setSpawnLocation(loc);
            }
            return;
        }
        WorldSettingsImpl worldSettings = plugin.getWorldManager().getSettings(event.getPlayer().getWorld());
        if (worldSettings != null)
        {
            Location loc = new Location(worldSettings.world(), worldSettings.spawnLocation().getX(), worldSettings.spawnLocation().getY(), worldSettings.spawnLocation().getZ(), worldSettings.spawnLocation().getYaw(), worldSettings.spawnLocation().getPitch());
            event.getPlayer().setBedSpawnLocation(loc);
            event.setSpawnLocation(loc);
        }
    }

}
