package io.github.matrixidot.magiCraft.api;

public class SpellCircuitBuilder {
    private SpellNode root;
    private SpellNode current;

    private SpellCircuitBuilder() {
        root = null;
        current = null;
    }

    public static SpellCircuitBuilder create() {
        return new SpellCircuitBuilder();
    }

    public SpellCircuitBuilder addNode(SpellNode node) {
        if (root == null) {
            root = node;
            current = node;
        } else {
            current.setNext(node);
            current = node;
        }
        return this;
    }

    public SpellNode build() {
        return root;
    }
}
