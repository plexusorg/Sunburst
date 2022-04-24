package dev.plex;

import dev.plex.command.impl.MessageCMD;
import dev.plex.command.impl.NicknameCMD;
import dev.plex.command.impl.ReplyCMD;
import dev.plex.command.impl.SunburstCMD;
import dev.plex.listener.impl.player.ChatListener;
import dev.plex.listener.impl.player.JoinListener;
import dev.plex.permission.PermissionHandlerImpl;
import dev.plex.player.ISunburstPlayer;
import dev.plex.player.PlayerCache;
import dev.plex.player.SunburstPlayer;
import dev.plex.plugin.SunburstPlugin;
import dev.plex.storage.FileStorage;
import dev.plex.util.ComponentUtil;
import dev.plex.util.Configuration;
import lombok.Getter;
import lombok.Setter;
import net.kyori.adventure.text.minimessage.MiniMessage;
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


    @Override
    public void load()
    {
        plugin = this;
        this.configuration = new Configuration(this, "config.yml");
        this.messages = new Configuration(this, "messages.yml");

        this.configuration.load();
        this.messages.load();

        this.getObjectHolder().setPermissionHandler(new PermissionHandlerImpl());
        this.getObjectHolder().setChatRenderer((source, sourceDisplayName, message, viewer) -> ComponentUtil.configComponent("chatFormat", MiniMessage.miniMessage().serialize(source.displayName()), PlainTextComponentSerializer.plainText().serialize(message)));
    }

    @Override
    public void onEnable()
    {
        this.getObjectHolder().setStorageSystem(new FileStorage());

        new JoinListener();
        new ChatListener();
        new NicknameCMD();
        new SunburstCMD();
        new MessageCMD();
        new ReplyCMD();

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
        plugin.getPlayerCache().getPlayers().forEach(sunburstPlayer -> {
            plugin.getObjectHolder().getStorageSystem().updatePlayer(sunburstPlayer);
        });
    }

    public static Sunburst inst()
    {
        return plugin;
    }
}
