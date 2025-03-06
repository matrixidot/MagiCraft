package io.github.matrixidot.magiCraft.api.control;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.AbstractEventBranch;
import io.github.matrixidot.magiCraft.api.BranchingNode;
import io.github.matrixidot.magiCraft.api.SpellContext;
import org.bukkit.Bukkit;

public class ForNode extends BranchingNode {
    private final long durationTicks;

    public ForNode(long durationTicks) {
        this.durationTicks = durationTicks;
    }

    @Override
    protected void performAction(SpellContext context) {
        // ForNode may send a message indicating that the lasting effect is active.
        context.getCaster().sendMessage("Lasting effect active for " + durationTicks/20 + " seconds.");
    }

    /**
     * Called by an attached branch when its event fires.
     * This method executes the branch’s child node, then resets the ForNode so it can trigger again.
     */
    @Override
    public void triggered(AbstractEventBranch branch, org.bukkit.event.Event event) {
        if (branch.getChildNode() != null) {
            branch.getChildNode().execute(context);
        }
        // Reset the triggered state so future damage events can trigger the effect.
        resetBranch();
    }

    @Override
    public void execute(SpellContext context) {
        super.execute(context); // Calls performAction() and registers branches.
        // Schedule the end of the lasting effect.
        Bukkit.getScheduler().runTaskLater(context.getPlugin(), () -> {
            // Unregister all branch listeners.
            getBranches().forEach(b -> b.unregister());
            context.getCaster().sendMessage("For thingy over");
            if (next != null) {
                next.execute(context);
            }
        }, durationTicks);
    }

    @Override
    public String getNodeType() {
        return "ForNode";
    }
}
