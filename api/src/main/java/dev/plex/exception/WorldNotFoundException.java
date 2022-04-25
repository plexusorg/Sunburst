package dev.plex.exception;

public class WorldNotFoundException extends RuntimeException
{
    public WorldNotFoundException()
    {
        super("<red>This world does not exist!");
    }
}
