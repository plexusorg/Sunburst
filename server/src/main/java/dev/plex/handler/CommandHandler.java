package dev.plex.handler;

import com.google.common.collect.Lists;
import dev.plex.command.SunburstCommand;
import dev.plex.util.Logger;
import dev.plex.util.ReflectionsUtil;
import java.lang.reflect.InvocationTargetException;
import java.util.List;
import java.util.Set;

// Reflections from Plex, done by Fleek
public class CommandHandler
{
    public CommandHandler()
    {
        Set<Class<? extends SunburstCommand>> commandSet = ReflectionsUtil.getClassesBySubType("dev.plex.command.impl", SunburstCommand.class);
        List<SunburstCommand> commands = Lists.newArrayList();

        commandSet.forEach(clazz ->
        {
            try
            {
                commands.add(clazz.getConstructor().newInstance());
            }
            catch (InvocationTargetException | InstantiationException | IllegalAccessException |
                   NoSuchMethodException ex)
            {
                Logger.error("Failed to register " + clazz.getSimpleName() + " as a command!");
            }
        });
        Logger.log(String.format("Registered %s commands from %s classes!", commands.size(), commandSet.size()));
    }
}
