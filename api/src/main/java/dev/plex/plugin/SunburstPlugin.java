package dev.plex.plugin;

import dev.plex.util.ObjectHolder;
import dev.plex.world.IWorldManager;
import org.bukkit.plugin.java.JavaPlugin;

public abstract class SunburstPlugin extends JavaPlugin
{
    private static SunburstPlugin plugin;
    private ObjectHolder holder;
    private IWorldManager<?> worldManager;

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

    public IWorldManager<?> getWorldManager()
    {
        return worldManager;
    }

    public static SunburstPlugin getPlugin()
    {
        return plugin;
    }

}
