package io.github.matrixidot.magiCraft.api;

import com.google.gson.JsonObject;

public abstract class SpellNode {
    protected SpellNode next; // Pointer to the next node

    public SpellNode setNext(SpellNode next) {
        this.next = next;
        return this;
    }

    public SpellNode getNext() {
        return next;
    }

    // Execute this node's behavior
    public abstract void execute(SpellContext context);

    // Return a unique identifier for this node type
    public abstract String getNodeType();
}
