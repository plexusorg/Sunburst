package dev.plex.listener.impl.player;

import dev.plex.listener.SunburstListener;
import io.papermc.paper.event.player.AsyncChatEvent;
import org.bukkit.event.EventHandler;
import org.bukkit.event.EventPriority;

public class ChatListener extends SunburstListener
{
    @EventHandler(priority = EventPriority.HIGHEST)
    public void onChat(AsyncChatEvent event)
    {
        if (!plugin.getConfiguration().getBoolean("options.chat"))
        {
            return;
        }
        event.renderer(plugin.getObjectHolder().getChatRenderer());
    }
}
