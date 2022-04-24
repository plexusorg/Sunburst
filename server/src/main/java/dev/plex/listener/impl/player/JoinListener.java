package dev.plex.listener.impl.player;

import dev.plex.Sunburst;
import dev.plex.listener.SunburstListener;
import dev.plex.player.ISunburstPlayer;
import dev.plex.player.SunburstPlayer;
import dev.plex.util.Logger;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.player.PlayerJoinEvent;
import org.bukkit.event.player.PlayerQuitEvent;

public class JoinListener extends SunburstListener
{
    @EventHandler
    public void createPlayer(PlayerJoinEvent event)
    {
        final Player player = event.getPlayer();
        ISunburstPlayer sunburstPlayer = plugin.getHolder().getStorageSystem().getPlayer(player.getUniqueId());
        if (sunburstPlayer == null)
        {
            sunburstPlayer = new SunburstPlayer(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress());
            plugin.getHolder().getStorageSystem().createPlayer(sunburstPlayer);
        }
        plugin.getPlayerCache().addPlayer(sunburstPlayer);
        if (sunburstPlayer.displayName() != null)
        {
            player.displayName(sunburstPlayer.displayName());
        }
    }

    @EventHandler
    public void removePlayer(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        plugin.getPlayerCache().getPlayer(player.getUniqueId()).ifPresent(sunburstPlayer -> {
            plugin.getHolder().getStorageSystem().updatePlayer(sunburstPlayer);
            plugin.getPlayerCache().removePlayer(sunburstPlayer);
        });
    }

}
