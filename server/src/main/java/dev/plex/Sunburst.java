package dev.plex;

import dev.plex.handler.CommandHandler;
import dev.plex.handler.ListenerHandler;
import dev.plex.permission.PermissionHandlerImpl;
import dev.plex.player.ISunburstPlayer;
import dev.plex.player.PlayerCache;
import dev.plex.player.SunburstPlayer;
import dev.plex.plugin.SunburstPlugin;
import dev.plex.storage.FileStorage;
import dev.plex.util.ComponentUtil;
import dev.plex.util.Configuration;
import dev.plex.util.Logger;
import dev.plex.world.JsonWorldManager;
import lombok.AccessLevel;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.serializer.plain.PlainTextComponentSerializer;
import org.bukkit.Bukkit;

@Getter
@Setter
public final class Sunburst extends SunburstPlugin
{
    private static Sunburst plugin;

    private final PlayerCache playerCache = new PlayerCache();

    private Configuration configuration;
    private Configuration messages;

    @Getter(AccessLevel.NONE)
    private JsonWorldManager jsonWorldManager;

    @Override
    public void load()
    {
        plugin = this;
        this.configuration = new Configuration(this, "config.yml");
        this.messages = new Configuration(this, "messages.yml");

        this.configuration.load();
        this.messages.load();

        this.getObjectHolder().setPermissionHandler(new PermissionHandlerImpl());
        this.getObjectHolder().setChatRenderer((source, sourceDisplayName, message, viewer) ->
        {
            Logger.log("Spokenz");
            Logger.log(ComponentUtil.mmCustom(sourceDisplayName, ComponentUtil.REGULAR_TAGS));
            return ComponentUtil.configComponent("chatFormat",
                    ComponentUtil.REGULAR_TAGS,
                    ComponentUtil.mmCustom(sourceDisplayName, ComponentUtil.REGULAR_TAGS),
                    PlainTextComponentSerializer.plainText().serialize(message));
        });

        Logger.debug("Loading");
    }

    @Override
    public void onEnable()
    {
        new ListenerHandler();
        new CommandHandler();

        this.getObjectHolder().setStorageSystem(new FileStorage());
        this.jsonWorldManager = new JsonWorldManager();

        Bukkit.getOnlinePlayers().forEach(player ->
        {
            ISunburstPlayer sunburstPlayer = plugin.getObjectHolder().getStorageSystem().getPlayer(player.getUniqueId());
            if (sunburstPlayer == null)
            {
                sunburstPlayer = new SunburstPlayer(player.getUniqueId(), player.getName(), player.getAddress().getAddress().getHostAddress());
                plugin.getObjectHolder().getStorageSystem().createPlayer(sunburstPlayer);
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
        plugin.getPlayerCache().getPlayers().forEach(sunburstPlayer ->
        {
            plugin.getObjectHolder().getStorageSystem().updatePlayer(sunburstPlayer);
        });
    }

    @Override
    public JsonWorldManager getWorldManager()
    {
        return jsonWorldManager;
    }

    public static Sunburst inst()
    {
        return plugin;
    }
}
