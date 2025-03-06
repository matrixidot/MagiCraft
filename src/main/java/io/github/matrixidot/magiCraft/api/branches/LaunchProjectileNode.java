package io.github.matrixidot.magiCraft.api.branches;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.*;
import org.bukkit.ChatColor;
import org.bukkit.entity.Arrow;
import org.bukkit.entity.Player;
import org.bukkit.event.Event;
import org.bukkit.util.Vector;
import org.bukkit.configuration.serialization.SerializableAs;

@SerializableAs("LaunchProjectileNode")
public class LaunchProjectileNode extends BranchingNode {

    public LaunchProjectileNode() {}

    @Override
    protected void performAction(SpellContext context) {
        Player caster = context.getCaster();
        caster.sendMessage(ChatColor.YELLOW + "Launching projectile in your looking direction...");
        Arrow arrow = caster.launchProjectile(Arrow.class);
        Vector direction = caster.getLocation().getDirection();
        arrow.setVelocity(direction.multiply(2));
        context.getPayload().put(SpellPayloadKey.PROJECTILE_ID, arrow.getUniqueId());
    }

    @Override
    public String getNodeType() {
        return "LaunchProjectileNode";
    }
}
