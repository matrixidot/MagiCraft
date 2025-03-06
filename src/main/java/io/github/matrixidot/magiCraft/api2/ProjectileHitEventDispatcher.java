package io.github.matrixidot.magiCraft.api2;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

public class ProjectileHitEventDispatcher implements Listener {
    private static final Map<Projectile, IEventComponent<ProjectileHitEvent>> callbacks = new HashMap<>();

    public static void addCallback(Projectile proj, IEventComponent<ProjectileHitEvent> component) {
        callbacks.put(proj, component);
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile proj = event.getEntity();
        IEventComponent<ProjectileHitEvent> component = callbacks.remove(proj);
        if (component != null) component.acceptEvent(event);
    }
}
