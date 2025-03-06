package io.github.matrixidot.magiCraft.api.control;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.ActionNode;
import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellNode;

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
}
