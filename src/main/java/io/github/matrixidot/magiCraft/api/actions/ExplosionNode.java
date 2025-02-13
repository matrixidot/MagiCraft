package io.github.matrixidot.magiCraft.api.actions;

import io.github.matrixidot.magiCraft.api.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("ExplosionNode")
public class ExplosionNode extends ActionNode {
    private final boolean fallbackToPlayerLocation;

    public ExplosionNode(boolean fallbackToPlayerLocation) {
        this.fallbackToPlayerLocation = fallbackToPlayerLocation;
    }
    @Override
    protected void performAction(SpellContext context) {
        Object locObj = context.getPayload().get(SpellPayloadKey.ACTION_LOCATION);
        Player caster = context.getCaster();
        if (locObj instanceof Location loc) {
            loc.getWorld().createExplosion(loc, 4f);
            // Actual explosion logic would go here.
        } else {
            if (fallbackToPlayerLocation) {
                caster.getWorld().createExplosion(caster.getLocation(), 4f);
            }
        }
    }

    @Override
    public String getNodeType() {
        return "ExplosionNode";
    }
}
