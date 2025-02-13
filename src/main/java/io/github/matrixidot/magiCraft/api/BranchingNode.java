package io.github.matrixidot.magiCraft.api;

import com.google.gson.JsonArray;
import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.EventBranchParent;

import java.util.ArrayList;
import java.util.List;

public abstract class BranchingNode extends SpellNode implements EventBranchParent {
    // A BranchingNode can have multiple concrete event branches.
    private final List<AbstractEventBranch> branches = new ArrayList<>();
    protected SpellContext context;
    private boolean triggered = false;

    public void addBranch(AbstractEventBranch branch) {
        branches.add(branch);
    }

    public List<AbstractEventBranch> getBranches() {
        return branches;
    }

    @Override
    public void execute(SpellContext context) {
        this.context = context;
        performAction(context);
        // Register each branch with this as parent.
        for (AbstractEventBranch branch : branches) {
            branch.register(context, this);
        }
    }

    protected abstract void performAction(SpellContext context);

    @Override
    public SpellContext getContext() {
        return context;
    }

    /**
     * Called by a branch when its event fires.
     */
    @Override
    public void triggered(AbstractEventBranch branch, org.bukkit.event.Event event) {
        if (triggered) return;
        triggered = true;
        // Unregister all branches.
        for (AbstractEventBranch b : branches) {
            b.unregister();
        }
        if (branch.getChildNode() != null) {
            branch.getChildNode().execute(context);
        }
        // In a traditional BranchingNode, no further node is automatically executed.
    }

    @Override
    public abstract String getNodeType();

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("nodeType", new JsonPrimitive(getNodeType()));
        if (next != null) {
            obj.add("next", next.serialize());
        }
        JsonArray branchArray = new JsonArray();
        for (AbstractEventBranch branch : branches) {
            branchArray.add(branch.serialize());
        }
        obj.add("branches", branchArray);
        return obj;
    }

    public void resetBranch() {
        triggered = false;
        for (AbstractEventBranch branch : branches) {
            branch.register(context, this);
        }
        branches.forEach(branch -> {
            SpellNode child = branch.getChildNode();
            while (!(child instanceof BranchingNode)) {
                if (child == null) break;
                child = child.getNext();
            }
            BranchingNode bn = (BranchingNode) child;
            if (bn != null)
                bn.resetBranch();
        });
    }
}
