package io.github.matrixidot.magiCraft;

import io.github.matrixidot.magiCraft.api.AbstractEventBranch;
import io.github.matrixidot.magiCraft.api.SpellContext;
import org.bukkit.event.Event;

public interface EventBranchParent {
    SpellContext getContext();
    /**
     * Called when a branch is triggered.
     */
    void triggered(AbstractEventBranch branch, Event event);
}
