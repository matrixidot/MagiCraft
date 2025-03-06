package io.github.matrixidot.magiCraft.api.actions;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.*;
import org.bukkit.ChatColor;
import org.bukkit.Location;
import org.bukkit.entity.Player;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("TeleportNode")
public class TeleportNode extends ActionNode {

    public TeleportNode() {}

    @Override
    protected void performAction(SpellContext context) {
        Object locObj = context.getPayload().get(SpellPayloadKey.ACTION_LOCATION);
        Player caster = context.getCaster();
        if (locObj instanceof Location loc) {
            caster.teleport(loc);
        }
    }

    @Override
    public String getNodeType() {
        return "TeleportNode";
    }
}
