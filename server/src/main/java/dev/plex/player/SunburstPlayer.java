package dev.plex.player;

import lombok.RequiredArgsConstructor;
import net.kyori.adventure.text.Component;
import org.bukkit.Bukkit;
import org.bukkit.entity.Player;

import java.util.UUID;

@RequiredArgsConstructor
public class SunburstPlayer implements ISunburstPlayer
{
    private final UUID uuid;
    private final String username;
    private final String ip;

    private Component displayName;

    private boolean godMode;
    private boolean muted;
    private boolean teleportToggled;
    private boolean afk;
    private boolean socialSpy;

    @Override
    public UUID uniqueId()
    {
        return uuid;
    }

    @Override
    public String username()
    {
        return username;
    }

    @Override
    public String ip()
    {
        return ip;
    }

    @Override
    public Component displayName()
    {
        return displayName;
    }

    @Override
    public boolean godMode()
    {
        return godMode;
    }

    @Override
    public boolean muted()
    {
        return muted;
    }

    @Override
    public boolean teleportToggled()
    {
        return teleportToggled;
    }

    @Override
    public boolean afk()
    {
        return afk;
    }

    @Override
    public boolean socialSpy()
    {
        return socialSpy;
    }

    @Override
    public void displayName(Component displayName)
    {
        this.displayName = displayName;
        Player player = Bukkit.getPlayer(uuid);
        if (player != null)
        {
            player.displayName(displayName);
        }
    }

    @Override
    public void godMode(boolean godMode)
    {
        this.godMode = godMode;
    }

    @Override
    public void muted(boolean muted)
    {
        this.muted = muted;
    }

    @Override
    public void teleportToggled(boolean toggle)
    {
        this.teleportToggled = toggle;
    }

    @Override
    public void afk(boolean afk)
    {
        this.afk = afk;
    }

    @Override
    public void socialSpy(boolean socialSpy)
    {
        this.socialSpy = socialSpy;
    }
}
