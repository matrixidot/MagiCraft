package io.github.matrixidot.magiCraft;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.ActionNode;
import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellNode;
import io.github.matrixidot.magiCraft.api.SpellSerializer;

public class CountedLoopNode extends SpellNode {
    private final int count;
    private final ActionNode loopBody;

    public CountedLoopNode(int count, ActionNode loopBody) {
        this.count = count;
        this.loopBody = loopBody;
    }

    @Override
    public void execute(SpellContext context) {
        for (int i = 0; i < count; i++) {
            loopBody.execute(context);
        }
        if (next != null) {
            next.execute(context);
        }
    }

    @Override
    public String getNodeType() {
        return "CountedLoopNode";
    }

    @Override
    public JsonObject serialize() {
        JsonObject obj = new JsonObject();
        obj.add("nodeType", new JsonPrimitive(getNodeType()));
        obj.addProperty("count", count);
        obj.add("loopBody", loopBody.serialize());
        if (next != null) {
            obj.add("next", next.serialize());
        }
        return obj;
    }

    public static CountedLoopNode deserialize(JsonObject obj) {
        int count = obj.get("count").getAsInt();
        ActionNode loopBody = (ActionNode) SpellSerializer.deserializeNode(obj.getAsJsonObject("loopBody"));
        CountedLoopNode node = new CountedLoopNode(count, loopBody);
        if (obj.has("next")) {
            node.next = SpellSerializer.deserializeNode(obj.getAsJsonObject("next"));
        }
        return node;
    }
}
