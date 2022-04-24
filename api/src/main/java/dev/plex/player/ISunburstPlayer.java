package dev.plex.player;

import com.google.gson.*;
import dev.plex.messaging.MessageData;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;

import java.lang.reflect.Type;
import java.util.UUID;

public interface ISunburstPlayer
{
    UUID uniqueId();

    String username();

    String ip();

    Component displayName();

    boolean godMode();

    boolean muted();

    boolean teleportToggled();

    boolean afk();

    boolean socialSpy();

    MessageData messageData();

    void displayName(Component displayName);

    void godMode(boolean godMode);

    void muted(boolean muted);

    void teleportToggled(boolean toggle);

    void afk(boolean afk);

    void socialSpy(boolean socialSpy);

    void messageData(MessageData messageData);

    default String toJSON()
    {
        return new GsonBuilder().setPrettyPrinting().registerTypeAdapter(Component.class, (JsonSerializer<Component>) (src, typeOfSrc, context) -> new JsonPrimitive(MiniMessage.miniMessage().serialize(src))).disableHtmlEscaping().create().toJson(this);
    }
}
