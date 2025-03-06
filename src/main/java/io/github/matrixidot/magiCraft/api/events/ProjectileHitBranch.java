package io.github.matrixidot.magiCraft.api.events;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.AbstractEventBranch;
import io.github.matrixidot.magiCraft.api.SpellNode;
import io.github.matrixidot.magiCraft.api.SpellPayloadKey;
import org.bukkit.Location;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.ProjectileHitEvent;

public class ProjectileHitBranch extends AbstractEventBranch<ProjectileHitEvent> {

    public ProjectileHitBranch(SpellNode childNode) {
        super(childNode);
    }

    @Override
    public Class<ProjectileHitEvent> getEventClass() {
        return ProjectileHitEvent.class;
    }

    @EventHandler
    public void onProjectileHit(ProjectileHitEvent event) {
        if (parent == null || parent.getContext() == null) return;
        Location hitLocation = null;
        if (event.getHitBlock() != null) {
            hitLocation = event.getHitBlock().getLocation();
        } else if (event.getHitEntity() != null) {
            hitLocation = event.getHitEntity().getLocation();
        }
        if (hitLocation != null) {
            parent.getContext().getPayload().put(SpellPayloadKey.ACTION_LOCATION, hitLocation);
        }
        parent.triggered(this, event);
    }
}
