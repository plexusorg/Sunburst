package dev.plex.storage;

import dev.plex.player.ISunburstPlayer;

import java.util.UUID;

public interface IStorage
{
    void createPlayer(ISunburstPlayer player);

    default void deletePlayer(ISunburstPlayer player)
    {
        deletePlayer(player.uniqueId());
    }

    void deletePlayer(UUID uuid);

    void updatePlayer(ISunburstPlayer player);

    ISunburstPlayer getPlayer(UUID uuid);
}
