package dev.plex;

import dev.plex.listener.impl.player.JoinListener;
import dev.plex.permission.IPermissionHandler;
import dev.plex.permission.PermissionHandlerImpl;
import dev.plex.player.PlayerCache;
import dev.plex.storage.FileStorage;
import dev.plex.util.Configuration;
import lombok.Getter;
import lombok.Setter;
import org.bukkit.plugin.java.JavaPlugin;

@Getter
@Setter
public final class Sunburst extends JavaPlugin
{
    private static Sunburst plugin;

    private final PlayerCache playerCache = new PlayerCache();

    private Configuration configuration;

    private IPermissionHandler permissionHandler;

    private FileStorage fileStorage;

    @Override
    public void onLoad()
    {
        plugin = this;
        this.configuration = new Configuration(this, "config.yml");
        this.permissionHandler = new PermissionHandlerImpl();
    }

    @Override
    public void onEnable()
    {
        this.fileStorage = new FileStorage();
        new JoinListener();
    }

    @Override
    public void onDisable()
    {
        // Plugin shutdown logic
    }

    public static Sunburst inst()
    {
        return plugin;
    }
}
