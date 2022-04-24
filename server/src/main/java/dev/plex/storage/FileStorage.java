package dev.plex.storage;

import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import dev.plex.Sunburst;
import dev.plex.exception.PlayerCreatedException;
import dev.plex.exception.PlayerDoesNotExistException;
import dev.plex.player.ISunburstPlayer;
import dev.plex.player.SunburstPlayer;
import lombok.SneakyThrows;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import org.json.JSONObject;
import org.json.JSONTokener;

import java.io.File;
import java.io.FileInputStream;
import java.io.FileWriter;
import java.util.UUID;

public class FileStorage implements IStorage
{
    private final Sunburst plugin = Sunburst.inst();

    private final File folder;

    public FileStorage()
    {
        if (!plugin.getDataFolder().exists())
        {
            plugin.getDataFolder().mkdir();
        }
        this.folder = new File(plugin.getDataFolder() + File.separator + "players");
        if (!this.folder.exists())
        {
            folder.mkdir();
        }
    }

    @SneakyThrows
    @Override
    public void createPlayer(ISunburstPlayer player)
    {
        File file = new File(folder, player.uniqueId().toString() + ".json");
        if (file.exists())
        {
            throw new PlayerCreatedException();
        }
        file.createNewFile();
        FileWriter writer = new FileWriter(file);
        writer.write(player.toJSON());
        writer.flush();
        writer.close();
    }

    @Override
    public void deletePlayer(UUID uuid)
    {
        File file = new File(folder, uuid.toString() + ".json");
        if (!file.exists())
        {
            throw new PlayerDoesNotExistException();
        }
        file.delete();
    }

    @SneakyThrows
    @Override
    public void updatePlayer(ISunburstPlayer player)
    {
        File file = new File(folder, player.uniqueId().toString() + ".json");
        if (!file.exists())
        {
            throw new PlayerDoesNotExistException();
        }
        FileWriter writer = new FileWriter(file);
        writer.write(player.toJSON());
        writer.flush();
        writer.close();
    }

    @SneakyThrows
    @Override
    public ISunburstPlayer getPlayer(UUID uuid)
    {
        ISunburstPlayer player = plugin.getPlayerCache().getPlayer(uuid).orElse(null);
        if (player != null)
        {
            return player;
        }
        File file = new File(folder, uuid.toString() + ".json");
        if (!file.exists())
        {
            return null;
        }
        try (FileInputStream fis = new FileInputStream(file))
        {
            JSONTokener tokener = new JSONTokener(fis);
            JSONObject object = new JSONObject(tokener);
            return new GsonBuilder().registerTypeAdapter(Component.class, (JsonDeserializer<Component>) (json, typeOfT, context) -> MiniMessage.miniMessage().deserialize(json.getAsJsonPrimitive().getAsString())).create().fromJson(object.toString(), SunburstPlayer.class);
        }
    }
}
