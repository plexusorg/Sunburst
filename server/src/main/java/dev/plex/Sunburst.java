package dev.plex;

import dev.plex.command.impl.NicknameCMD;
import dev.plex.command.impl.SunburstCMD;
import dev.plex.listener.impl.player.ChatListener;
import dev.plex.listener.impl.player.JoinListener;
import dev.plex.permission.PermissionHandlerImpl;
import dev.plex.player.ISunburstPlayer;
import dev.plex.player.PlayerCache;
import dev.plex.player.SunburstPlayer;
import dev.plex.storage.FileStorage;
import dev.plex.util.Configuration;
import dev.plex.util.ObjectHolder;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.Bukkit;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class Sunburst extends JavaPlugin
{
    private static Sunburst plugin;

    private final PlayerCache playerCache = new PlayerCache();

    private Configuration configuration;
    private Configuration messages;

    private ObjectHolder holder;

    @Override
    public void onLoad()
    {
        plugin = this;
        this.configuration = new Configuration(this, "config.yml");
        this.messages = new Configuration(this, "messages.yml");

        this.configuration.load();
        this.messages.load();

        this.holder = new ObjectHolder();
        this.holder.setPermissionHandler(new PermissionHandlerImpl());
        this.holder.setChatRenderer(holder);
    }

    @Override
    public void onEnable()
    {
        this.holder.setStorageSystem(new FileStorage());

        new JoinListener();
        new ChatListener();
        new NicknameCMD();
        new SunburstCMD();

        Bukkit.getOnlinePlayers().forEach(player ->
        {
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
        });
    }

    @Override
    public void onDisable()
    {
        plugin.getPlayerCache().getPlayers().forEach(sunburstPlayer -> {
            plugin.getHolder().getStorageSystem().updatePlayer(sunburstPlayer);
        });
    }

    public static Sunburst inst()
    {
        return plugin;
    }
}
