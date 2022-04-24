package dev.plex.listener;

import dev.plex.Sunburst;
import org.bukkit.event.Listener;

public abstract class SunburstListener implements Listener
{
    protected final Sunburst plugin;

    public SunburstListener()
    {
        plugin = Sunburst.inst();
        plugin.getServer().getPluginManager().registerEvents(this, plugin);
    }
}
