package dev.plex.world;

import com.google.gson.GsonBuilder;
import dev.plex.util.XYZLocation;
import dev.plex.util.gson.XYZLocationAdapter;
import org.bukkit.Bukkit;
import org.bukkit.World;

public class WorldSettingsImpl implements IWorldSettings
{
    private String worldName;
    private XYZLocation spawnLocation;

    @Override
    public World world()
    {
        return Bukkit.getWorld(worldName);
    }

    @Override
    public XYZLocation spawnLocation()
    {
        return spawnLocation;
    }

    public void world(World world)
    {
        this.worldName = world.getName();
    }

    @Override
    public void spawnLocation(XYZLocation location)
    {
        this.spawnLocation = location;
    }

    public String toJSON()
    {
        return new GsonBuilder().setPrettyPrinting()
                .registerTypeAdapter(XYZLocation.class, new XYZLocationAdapter())
                .create()
                .toJson(this);
    }

    public static WorldSettingsImpl fromJSON(String string)
    {
        return new GsonBuilder()
                .registerTypeAdapter(XYZLocation.class, new XYZLocationAdapter())
                .create()
                .fromJson(string, WorldSettingsImpl.class);
    }
}
