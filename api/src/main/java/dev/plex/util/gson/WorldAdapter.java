package dev.plex.util.gson;

import com.google.gson.TypeAdapter;
import com.google.gson.stream.JsonReader;
import com.google.gson.stream.JsonToken;
import com.google.gson.stream.JsonWriter;
import org.bukkit.Bukkit;
import org.bukkit.World;

import java.io.IOException;

public class WorldAdapter extends TypeAdapter<World>
{
    @Override
    public void write(JsonWriter out, World value) throws IOException
    {
        if (value == null)
        {
            out.nullValue();
            return;
        }
        out.value(value.getName());
    }

    @Override
    public World read(JsonReader reader) throws IOException
    {
        if (reader.peek() == JsonToken.NULL)
        {
            reader.nextNull();
            return null;
        }
        String worldName = reader.nextString();
        return Bukkit.getWorld(worldName);
    }
}
