package dev.plex.exception;

public class ConfigValueNotFoundException extends RuntimeException
{
    public ConfigValueNotFoundException()
    {
        super("<red>This configured value does not exist!");
    }
}
