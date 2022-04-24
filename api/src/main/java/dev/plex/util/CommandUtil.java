package dev.plex.util;

import dev.plex.exception.CommandNotFoundException;
import org.bukkit.Bukkit;
import org.bukkit.command.Command;

public class CommandUtil
{

    public static void unregisterCommand(String commandName)
    {
        Command command = Bukkit.getCommandMap().getCommand(commandName);
        if (command == null)
        {
            throw new CommandNotFoundException();
        }
        command.getAliases().forEach(s -> Bukkit.getCommandMap().getKnownCommands().remove(s));
        Bukkit.getCommandMap().getKnownCommands().remove(command.getName());
    }

}
