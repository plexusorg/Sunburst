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

    public static String miniString(Component component)
    {
        return mmCustom(component, TagResolver.standard());
    }

    public static String mmCustom(Component component, TagResolver... tags)
    {
        return MiniMessage.builder().tags(TagResolver.resolver(tags)).build().serialize(component);
    }

    public static Component mini(String message)
    {
        return mmCustom(message, TagResolver.standard());
    }

    public static Component mmCustom(String message, TagResolver... tags)
    {
        return MiniMessage.builder().tags(TagResolver.resolver(tags)).build().deserialize(message);
    }

    public static Component configComponent(String entry, TagResolver[] tagResolvers, Object... objects)
    {
        return MiniMessage.builder().tags(TagResolver.resolver(tagResolvers)).build().deserialize(configMessage(entry, objects));
    }

    public static Component configComponent(String entry, Object... objects)
    {
        return MiniMessage.miniMessage().deserialize(configMessage(entry, objects));
    }

}
