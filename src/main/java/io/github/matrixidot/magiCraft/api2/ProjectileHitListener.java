package io.github.matrixidot.magiCraft.api2;

import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.concurrent.CompletableFuture;

public class ProjectileHitListener implements Listener {

    private static final HashMap<Projectile, CompletableFuture<ProjectileHitEvent>> callbacks = new HashMap<>();

    public static void registerCallback(Projectile proj, CompletableFuture<ProjectileHitEvent> cf) {
        callbacks.put(proj, cf);
    }
    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        Projectile proj = event.getEntity();
        CompletableFuture<ProjectileHitEvent> future = callbacks.remove(proj);
        if (future != null) future.complete(event);
    }
}
