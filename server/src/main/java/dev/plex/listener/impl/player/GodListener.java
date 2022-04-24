package dev.plex.listener.impl.player;

import dev.plex.listener.SunburstListener;
import dev.plex.player.ISunburstPlayer;
import org.bukkit.entity.EntityType;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageEvent;

public class GodListener extends SunburstListener
{
    @EventHandler
    public void onDamage(EntityDamageEvent event)
    {
        if (event.getEntityType() != EntityType.PLAYER)
        {
            return;
        }
        ISunburstPlayer player = plugin.getObjectHolder().getStorageSystem().getPlayer(event.getEntity().getUniqueId());
        if (player.godMode())
        {
            event.setDamage(0);
            event.setCancelled(true);
        }
    }
}
