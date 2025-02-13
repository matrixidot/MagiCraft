package io.github.matrixidot.magiCraft.api;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;

public abstract class ActionNode extends SpellNode {

    // Subclasses implement this to perform an immediate action.
    protected abstract void performAction(SpellContext context);

    @Override
    public void execute(SpellContext context) {
        performAction(context);
        if (next != null) next.execute(context);
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("nodeType", new JsonPrimitive(getNodeType()));
        if (next != null) {
            obj.add("next", next.serialize());
        }
        return obj;
    }
}
