package dev.plex.listener.impl.player;

import dev.plex.listener.SunburstListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;

public class ChatListener extends SunburstListener
{
    @EventHandler
    public void onChat(AsyncChatEvent event)
    {
        if (!plugin.getConfiguration().getBoolean("options.chat"))
        {
            return;
        }
        event.renderer(plugin.getHolder().getChatRenderer());
    }
}
