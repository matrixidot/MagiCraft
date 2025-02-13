package io.github.matrixidot.magiCraft.api.actions;

import io.github.matrixidot.magiCraft.api.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("LightningStrikeNode")
public class LightningStrikeNode extends ActionNode {
    private final boolean fallbackToPlayerLocation;

    public LightningStrikeNode(boolean fallbackToPlayerLocation) {
        this.fallbackToPlayerLocation = fallbackToPlayerLocation;
    }

    @Override
    protected void performAction(SpellContext context) {
        Object locObj = context.getPayload().get(SpellPayloadKey.ACTION_LOCATION);
        Player caster = context.getCaster();
        if (locObj instanceof Location loc) {
            loc.getWorld().strikeLightning(loc);
        } else {
            if (fallbackToPlayerLocation) {
                caster.getWorld().strikeLightning(caster.getLocation());
            }
        }
    }

    @Override
    public String getNodeType() {
        return "LightningStrikeNode";
    }
}
