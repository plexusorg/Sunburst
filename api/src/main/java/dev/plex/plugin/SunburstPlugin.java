package dev.plex.plugin;

import dev.plex.util.ObjectHolder;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SunburstPlugin extends JavaPlugin
{
    private static SunburstPlugin plugin;
    private ObjectHolder holder;

    @Override
    public void onLoad()
    {
        plugin = this;
        this.holder = new ObjectHolder();
        load();
    }

    public abstract void load();

    public ObjectHolder getObjectHolder()
    {
        return this.holder;
    }

    public static SunburstPlugin getPlugin()
    {
        return plugin;
    }
}
