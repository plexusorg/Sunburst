package dev.plex.world;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonPrimitive;
import com.google.gson.JsonSerializer;
import dev.plex.util.XYZLocation;
import dev.plex.util.gson.WorldAdapter;
import dev.plex.util.gson.XYZLocationAdapter;
import org.bukkit.World;

public interface IWorldSettings
{
    World world();

    XYZLocation spawnLocation();
    void spawnLocation(XYZLocation location);
}
