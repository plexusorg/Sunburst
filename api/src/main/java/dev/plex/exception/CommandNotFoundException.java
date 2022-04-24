package dev.plex.exception;

public class CommandNotFoundException extends RuntimeException
{
    public CommandNotFoundException()
    {
        super("<red>This command does not exist!");
    }
}
