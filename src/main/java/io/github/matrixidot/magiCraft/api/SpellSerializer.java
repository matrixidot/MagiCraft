package io.github.matrixidot.magiCraft.api;

import com.google.gson.Gson;
import com.google.gson.JsonObject;
import com.google.gson.JsonParser;
import io.github.matrixidot.magiCraft.api.actions.ExplosionNode;
import io.github.matrixidot.magiCraft.api.actions.LightningStrikeNode;
import io.github.matrixidot.magiCraft.api.actions.TeleportNode;
import io.github.matrixidot.magiCraft.api.branches.LaunchProjectileNode;
import io.github.matrixidot.magiCraft.api.events.PlayerInteractBranch;
import io.github.matrixidot.magiCraft.api.events.ProjectileHitBranch;
import org.bukkit.NamespacedKey;
import org.bukkit.persistence.PersistentDataContainer;
import org.bukkit.persistence.PersistentDataType;

public class SpellSerializer {
    private static final Gson gson = new Gson();

    public static String serializeSpell(SpellNode node) {
        JsonObject json = node.serialize();
        return gson.toJson(json);
    }

    public static SpellNode deserializeSpell(String data) {
        JsonObject json = JsonParser.parseString(data).getAsJsonObject();
        return deserializeNode(json);
    }

    public static SpellNode deserializeNode(JsonObject json) {
//        String nodeType = json.get("nodeType").getAsString();
//        switch (nodeType) {
//            case "ExplosionNode":
//                return ExplosionNode.deserialize(json);
//            case "LightningStrikeNode":
//                return LightningStrikeNode.deserialize(json);
//            case "TeleportNode":
//                return TeleportNode.deserialize(json);
//            case "LaunchProjectileNode":
//                return LaunchProjectileNode.deserialize(json);
//            default:
//                throw new IllegalArgumentException("Unknown node type: " + nodeType);
//        }
        return null;
    }

    // Helper to deserialize an event branch from a JsonObject.
    public static AbstractEventBranch deserializeEventBranch(JsonObject json) {
        String branchType = json.get("branchType").getAsString();
        switch (branchType) {
            case "ProjectileHitBranch":
                return ProjectileHitBranch.deserialize(json);
            case "PlayerInteractBranch":
                return PlayerInteractBranch.deserialize(json);
            default:
                throw new IllegalArgumentException("Unknown branch type: " + branchType);
        }
    }

    public static void writeSpellToPDC(PersistentDataContainer container, NamespacedKey key, SpellNode node) {
        String serialized = serializeSpell(node);
        container.set(key, PersistentDataType.STRING, serialized);
    }

    public static SpellNode readSpellFromPDC(PersistentDataContainer container, NamespacedKey key) {
        if (container.has(key, PersistentDataType.STRING)) {
            String serialized = container.get(key, PersistentDataType.STRING);
            return deserializeSpell(serialized);
        }
        return null;
    }
}
