package io.github.matrixidot.magiCraft.api2;

import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

public class EventListener implements Listener {
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        GenericEventDispatcher.dispatchEvent(event);
    }

    @EventHandler
    public void onEntityDamageByEntity(EntityDamageByEntityEvent event) {
        GenericEventDispatcher.dispatchEvent(event);
    }
}
