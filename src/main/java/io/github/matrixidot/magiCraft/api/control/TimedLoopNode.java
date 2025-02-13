package io.github.matrixidot.magiCraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.ActionNode;
import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellNode;
import io.github.matrixidot.magiCraft.api.SpellSerializer;
import org.bukkit.Bukkit;

public class TimedLoopNode extends SpellNode {
    private final ActionNode loopBody;
    private final long durationTicks;
    private final long delayTicks;

    public TimedLoopNode(ActionNode loopBody, long durationTicks, long delayTicks) {
        this.loopBody = loopBody;
        this.durationTicks = durationTicks;
        this.delayTicks = delayTicks;
    }

    @Override
    public void execute(SpellContext context) {
        // Schedule a repeating task to execute the loop body.
        int taskId = Bukkit.getScheduler().runTaskTimer(context.getPlugin(), () -> {
            loopBody.execute(context);
        }, 0, delayTicks).getTaskId();

        // Schedule a task to cancel the repeating task after durationTicks,
        // then continue with the next node.
        Bukkit.getScheduler().runTaskLater(context.getPlugin(), () -> {
            Bukkit.getScheduler().cancelTask(taskId);
            if (next != null) {
                next.execute(context);
            }
        }, durationTicks);
    }

    @Override
    public String getNodeType() {
        return "TimedLoopNode";
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("nodeType", new JsonPrimitive(getNodeType()));
        obj.add("loopBody", loopBody.serialize());
        obj.addProperty("durationTicks", durationTicks);
        obj.addProperty("delayTicks", delayTicks);
        if (next != null) {
            obj.add("next", next.serialize());
        }
        return obj;
    }

    public static TimedLoopNode deserialize(JsonObject obj) {
        ActionNode loopBody = (ActionNode) SpellSerializer.deserializeNode(obj.getAsJsonObject("loopBody"));
        long durationTicks = obj.get("durationTicks").getAsLong();
        long delayTicks = obj.get("delayTicks").getAsLong();
        TimedLoopNode node = new TimedLoopNode(loopBody, durationTicks, delayTicks);
        if (obj.has("next")) {
            node.next = SpellSerializer.deserializeNode(obj.getAsJsonObject("next"));
        }
        return node;
    }
}
