package dev.plex.world;

import org.bukkit.World;

import java.util.Collection;

public interface IWorldManager<T extends IWorldSettings>
{
    void addSetting(T settings);

    void removeSettings(T settings);

    T getSettings(World world);

    Collection<T> getSettings();

    void clear();
}
