package dev.plex.player;

import com.google.common.collect.Maps;

import java.util.*;

public class PlayerCache
{
    private static final Map<UUID, ISunburstPlayer> PLAYERS = Maps.newHashMap();

    public void addPlayer(ISunburstPlayer player)
    {
        PLAYERS.put(player.uniqueId(), player);
    }

    public void removePlayer(ISunburstPlayer player)
    {
        removePlayer(player.uniqueId());
    }

    public Optional<ISunburstPlayer> getPlayer(UUID uuid)
    {
        return Optional.ofNullable(PLAYERS.get(uuid));
    }

    public Collection<ISunburstPlayer> getPlayers()
    {
        return PLAYERS.values();
    }

    public void removePlayer(UUID uuid)
    {
        PLAYERS.remove(uuid);
    }

}
