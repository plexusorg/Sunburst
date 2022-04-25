package dev.plex.world;

import com.google.common.collect.Lists;
import com.google.gson.GsonBuilder;
import dev.plex.Sunburst;
import dev.plex.util.Logger;
import dev.plex.util.XYZLocation;
import dev.plex.util.gson.XYZLocationAdapter;
import lombok.SneakyThrows;
import org.bukkit.World;
import org.json.JSONArray;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.Collection;
import java.util.List;
import java.util.stream.Collectors;

public class JsonWorldManager implements IWorldManager<WorldSettingsImpl>
{
    private static final List<WorldSettingsImpl> WORLD_SETTINGS = Lists.newArrayList();
    private final Sunburst plugin = Sunburst.inst();
    private final File file;

    @SneakyThrows
    public JsonWorldManager()
    {
        this.file = new File(plugin.getDataFolder(), "worlds.json");
        if (!this.file.exists())
        {
            this.file.createNewFile();
            try (FileWriter writer = new FileWriter(this.file))
            {
                writer.write("[]");
                writer.flush();
            }
        } else
        {
            this.loadAll();
        }

    }

    @SneakyThrows
    public void loadAll()
    {
        clear();
        try (FileInputStream fis = new FileInputStream(this.file))
        {
            final JSONTokener tokener = new JSONTokener(fis);
            final JSONArray array = new JSONArray(tokener);
            array.forEach(object ->
            {
                final JSONObject obj = new JSONObject(object.toString());
                addSetting(WorldSettingsImpl.fromJSON(obj.toString()));
            });
        }
    }

    @SneakyThrows
    public void writeSettings()
    {
        try (FileWriter writer = new FileWriter(this.file))
        {
            String json = new GsonBuilder().setPrettyPrinting().registerTypeAdapter(XYZLocation.class, new XYZLocationAdapter())
                .create().toJson(getSettings());
            writer.write(json);
            writer.flush();
        }
    }

    public void addSetting(WorldSettingsImpl settings)
    {
        WORLD_SETTINGS.add(settings);
    }

    public void removeSettings(WorldSettingsImpl settings)
    {
        WORLD_SETTINGS.remove(settings);
    }

    public WorldSettingsImpl getSettings(World world)
    {
        return WORLD_SETTINGS.stream().filter(settings -> settings.world().equals(world)).findFirst().orElse(null);
    }

    public Collection<WorldSettingsImpl> getSettings()
    {
        return WORLD_SETTINGS.stream().toList();
    }

    public void clear()
    {
        WORLD_SETTINGS.clear();
    }
}
