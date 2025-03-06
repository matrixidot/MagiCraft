package io.github.matrixidot.magiCraft.api2;

import org.bukkit.Location;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.concurrent.CompletableFuture;

public class ProjectileHitComponent extends MGCComponent {
    public ProjectileHitComponent() {
        super(new Class[]{CompletableFuture.class}, new Class[] {CompletableFuture.class});
    }

    @Override
    public Object[] run(Object[] inputs) {
        if (!validateInputs(inputs)) return null;
        CompletableFuture<ProjectileHitEvent> onHit = (CompletableFuture<ProjectileHitEvent>) inputs[0];
        return new CompletableFuture[]{onHit.thenApply(e -> e.getEntity().getLocation())};
    }
}
