package io.github.matrixidot.magiCraft.api2;

import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.entity.Projectile;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.concurrent.CompletableFuture;

public class ProjectileLaunchComponent extends MGCComponent {
    private final Class<? extends Projectile> projectileType;
    public ProjectileLaunchComponent() {
        this(Arrow.class);
    }

    public ProjectileLaunchComponent(Class<? extends Projectile> projectileType) {
        super(new Class[] {Player.class}, new Class[] {CompletableFuture.class});
        this.projectileType = projectileType;
    }


    @Override
    public Object[] run(Object[] inputs) {
        if (!validateInputs(inputs)) return null;
        Projectile proj = ((Player) inputs[0]).launchProjectile(projectileType);
        CompletableFuture<ProjectileHitEvent> onHit = new CompletableFuture<>();
        ProjectileHitListener.registerCallback(proj, onHit);
        return new CompletableFuture[]{onHit};
    }
}
