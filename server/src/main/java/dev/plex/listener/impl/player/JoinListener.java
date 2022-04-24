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
        ISunburstPlayer sunburstPlayer = plugin.getFileStorage().getPlayer(player.getUniqueId());
        if (sunburstPlayer == null)
        {
            sunburstPlayer = new SunburstPlayer(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress());
            plugin.getFileStorage().createPlayer(sunburstPlayer);
        }
        plugin.getPlayerCache().addPlayer(sunburstPlayer);
    }

    @EventHandler
    public void removePlayer(PlayerQuitEvent event)
    {
        final Player player = event.getPlayer();
        plugin.getPlayerCache().getPlayer(player.getUniqueId()).ifPresent(sunburstPlayer -> {
            Logger.debug("Found cached player");
            sunburstPlayer.displayName(MiniMessage.miniMessage().deserialize("<red>Test"));
            Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().serialize(sunburstPlayer.displayName()));
            Bukkit.getConsoleSender().sendMessage(MiniMessage.miniMessage().serialize(MiniMessage.miniMessage().deserialize("<red>Test")));
            plugin.getFileStorage().updatePlayer(sunburstPlayer);
            plugin.getPlayerCache().removePlayer(sunburstPlayer);
        });
    }

}
