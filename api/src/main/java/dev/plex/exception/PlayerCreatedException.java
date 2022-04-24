package dev.plex.exception;

public class PlayerCreatedException extends RuntimeException
{
    public PlayerCreatedException()
    {
        super("<red>This player already exists in this storage!");
    }
}
