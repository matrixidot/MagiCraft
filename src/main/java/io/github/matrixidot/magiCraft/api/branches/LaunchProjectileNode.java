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

    // Deserialize a LaunchProjectileNode from a JsonObject.
    public static LaunchProjectileNode deserialize(JsonObject obj) {
        LaunchProjectileNode node = new LaunchProjectileNode();
        if (obj.has("next")) {
            node.next = SpellSerializer.deserializeNode(obj.getAsJsonObject("next"));
        }
        if (obj.has("branches")) {
            JsonArray branchArray = obj.getAsJsonArray("branches");
            for (int i = 0; i < branchArray.size(); i++) {
                JsonObject branchObj = branchArray.get(i).getAsJsonObject();
                // Use the helper in SpellSerializer to deserialize the branch.
                AbstractEventBranch branch = SpellSerializer.deserializeEventBranch(branchObj);
                if (branch != null) {
                    node.addBranch(branch);
                }
            }
        }
        return node;
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("nodeType", new JsonPrimitive(getNodeType()));
        if (next != null) {
            obj.add("next", next.serialize());
        }
        // Serialize branches as a JSON array.
        JsonArray branchArray = new JsonArray();
        for (AbstractEventBranch branch : getBranches()) {
            branchArray.add(branch.serialize());
        }
        obj.add("branches", branchArray);
        return obj;
    }
}
