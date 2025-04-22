package io.github.matrixidot.magiCraft;

import io.github.matrixidot.magiCraft.api.EventChain;
import org.bukkit.Bukkit;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Projectile;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MagiCraft extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @EventHandler
    public void onClick(PlayerInteractEvent click) {
        Projectile arrow = click.getPlayer().launchProjectile(Arrow.class);

        EventChain.begin(this, ProjectileHitEvent.class, hit -> hit.getEntity().getUniqueId().equals(arrow.getUniqueId()))
            .eventFired(hit -> {
                if (hit.getHitBlock() != null) {
                    hit.getHitBlock().getLocation().createExplosion(arrow, 4);
                    arrow.remove();
                } else {
                    EventChain.begin(this, EntityDamageByEntityEvent.class, dmg -> dmg.getDamager().getUniqueId().equals(arrow.getUniqueId()))
                            .eventFired(dmg -> dmg.getEntity().getWorld().strikeLightning(dmg.getEntity().getLocation()));
                }
            }).except(Throwable::printStackTrace);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

}
