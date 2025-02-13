package io.github.matrixidot.magiCraft.api.control;

import com.google.gson.JsonObject;
import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellNode;
import org.bukkit.Bukkit;

public class DelayNode extends SpellNode {
    private final long delayTicks;

    public DelayNode(long delayTicks) {
        this.delayTicks = delayTicks;
    }

    @Override
    public void execute(SpellContext context) {
        Bukkit.getScheduler().runTaskLater(context.getPlugin(), task -> {
            if (next != null) next.execute(context);
        }, delayTicks);
    }

    @Override
    public String getNodeType() {
        return "DelayNode";
    }

    @Override
    public JsonObject serialize() {
        return null;
    }
}
