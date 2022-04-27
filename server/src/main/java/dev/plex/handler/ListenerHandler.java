package dev.plex.handler;

import com.google.common.collect.Lists;
import dev.plex.listener.SunburstListener;
import dev.plex.util.Logger;
import dev.plex.util.ReflectionsUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

// Reflections from Plex, done by Fleek
public class ListenerHandler
{
    public ListenerHandler()
    {
        Set<Class<? extends SunburstListener>> listenerSet = ReflectionsUtil.getClassesBySubType("dev.plex.listener.impl.player", SunburstListener.class);
        List<SunburstListener> listeners = Lists.newArrayList();

        listenerSet.forEach(clazz ->
        {
            try
            {
                listeners.add(clazz.getConstructor().newInstance());
            }
            catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                   NoSuchMethodException ex)
            {
                Logger.error("Failed to register " + clazz.getSimpleName() + " as a listener!");
            }
        });
        Logger.log(String.format("Registered %s listeners from %s classes!", listeners.size(), listenerSet.size()));
    }
}
