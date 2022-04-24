package dev.plex.util;

import dev.plex.Sunburst;
import dev.plex.exception.ConfigValueNotFoundException;
import net.kyori.adventure.text.Component;
import net.kyori.adventure.text.minimessage.MiniMessage;
import net.kyori.adventure.text.minimessage.tag.resolver.TagResolver;
import net.kyori.adventure.text.minimessage.tag.standard.StandardTags;

public class ComponentUtil
{
    public static final TagResolver[] REGULAR_TAGS = {
            StandardTags.color(),
            StandardTags.decorations(),
            StandardTags.gradient(),
            StandardTags.rainbow(),
            StandardTags.transition()
    };

    public static String configMessage(String entry, Object... objects)
    {
        String value = Sunburst.inst().getMessages().getString(entry);
        if (value == null)
        {
            throw new ConfigValueNotFoundException();
        }
        for (int i = 0; i < objects.length; i++)
        {
            value = value.replace("{" + i + "}", String.valueOf(objects[i]));
        }
        return value;
    }

    public static Component configComponent(String entry, Object... objects)
    {
        return MiniMessage.miniMessage().deserialize(configMessage(entry, objects));
    }

}
