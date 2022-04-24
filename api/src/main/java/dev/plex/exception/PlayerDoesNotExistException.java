package dev.plex.exception;

public class PlayerDoesNotExistException extends RuntimeException
{
    public PlayerDoesNotExistException()
    {
        super("<red>This player doesn't exist in this storage!");
    }
}
