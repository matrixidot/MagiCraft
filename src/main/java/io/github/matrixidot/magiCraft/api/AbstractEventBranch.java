package io.github.matrixidot.magiCraft.api;

import com.google.gson.JsonObject;
import io.github.matrixidot.magiCraft.EventBranchParent;
import org.bukkit.event.HandlerList;
import org.bukkit.event.Listener;
import org.bukkit.plugin.Plugin;
import org.bukkit.plugin.PluginManager;
import org.bukkit.event.Event;

public abstract class AbstractEventBranch<T extends Event> implements Listener {
    // The child node to execute when the event is triggered.
    private SpellNode childNode;
    // Now store the parent as an EventBranchParent.
    protected EventBranchParent parent;

    public AbstractEventBranch(SpellNode childNode) {
        this.childNode = childNode;
    }

    public SpellNode getChildNode() {
        return childNode;
    }

    public void setChildNode(SpellNode childNode) {
        this.childNode = childNode;
    }

    public abstract Class<T> getEventClass();

    /**
     * Registers this branch as an event listener with the server.
     */
    public void register(SpellContext context, EventBranchParent parent) {
        this.parent = parent;
        Plugin plugin = context.getPlugin();
        PluginManager pm = plugin.getServer().getPluginManager();
        pm.registerEvents(this, plugin);
    }

    /**
     * Unregisters this branch.
     */
    public void unregister() {
        HandlerList.unregisterAll(this);
    }


    public abstract JsonObject serialize();
}
