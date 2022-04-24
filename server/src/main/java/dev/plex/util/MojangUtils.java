package dev.plex.util;

import com.google.common.collect.Maps;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonDeserializer;
import java.io.IOException;
import java.nio.charset.StandardCharsets;
import java.time.Instant;
import java.time.ZoneId;
import java.time.ZonedDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;
import java.util.Map;

import dev.plex.Sunburst;
import org.apache.http.HttpResponse;
import org.apache.http.client.methods.HttpGet;
import org.apache.http.impl.client.CloseableHttpClient;
import org.apache.http.impl.client.HttpClients;
import org.apache.http.util.EntityUtils;
import org.bukkit.scheduler.BukkitRunnable;
import org.json.JSONObject;

public class MojangUtils
{
    private static final Map<String, AshconInfo> CACHED_INFO = Maps.newHashMap();
    public static AshconInfo getInfo(String nameOrUuid)
    {
        if (CACHED_INFO.containsKey(nameOrUuid))
        {
            return CACHED_INFO.get(nameOrUuid);
        }
        CloseableHttpClient client = HttpClients.createDefault();
        HttpGet get = new HttpGet("https://api.ashcon.app/mojang/v2/user/" + nameOrUuid);
        try
        {
            HttpResponse response = client.execute(get);
            if (response == null || response.getEntity() == null)
            {
                return null;
            }
            String json = EntityUtils.toString(response.getEntity(), StandardCharsets.UTF_8);
            JSONObject object = new JSONObject(json);
            if (!object.isNull("code") && object.getInt("code") == 404)
            {
                return null;
            }
            client.close();
            AshconInfo ashconInfo = new GsonBuilder().registerTypeAdapter(ZonedDateTime.class, (JsonDeserializer<ZonedDateTime>)(json1, typeOfT, context) ->
                    ZonedDateTime.ofInstant(Instant.from(DateTimeFormatter.ISO_INSTANT.parse(json1.getAsJsonPrimitive().getAsString())), ZoneId.systemDefault())).create().fromJson(json, AshconInfo.class);

            CACHED_INFO.put(ashconInfo.getUuid(), ashconInfo);
            CACHED_INFO.put(ashconInfo.getUsername(), ashconInfo);

            new BukkitRunnable()
            {
                @Override
                public void run()
                {
                    CACHED_INFO.remove(ashconInfo.getUsername());
                    CACHED_INFO.remove(ashconInfo.getUuid());
                }
            }.runTaskLater(Sunburst.inst(), 20 * 60 * 2);

            Arrays.sort(ashconInfo.getUsernameHistories(), (o1, o2) ->
            {
                if (o1.getZonedDateTime() == null || o2.getZonedDateTime() == null)
                {
                    return 1;
                }
                return o1.getZonedDateTime().compareTo(o2.getZonedDateTime());
            });

            return ashconInfo;
        }
        catch (IOException e)
        {
            e.printStackTrace();
            return null;
        }
    }
}