package io.github.matrixidot.magiCraft.api.events;

import com.google.gson.JsonObject;
import com.google.gson.JsonPrimitive;
import io.github.matrixidot.magiCraft.api.AbstractEventBranch;
import io.github.matrixidot.magiCraft.api.SpellNode;
import org.bukkit.event.EventHandler;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;

public class PlayerInteractBranch extends AbstractEventBranch<PlayerInteractEvent> {

    public PlayerInteractBranch(SpellNode childNode) {
        super(childNode);
    }

    @Override
    public Class<PlayerInteractEvent> getEventClass() {
        return PlayerInteractEvent.class;
    }

    @EventHandler
    public void onPlayerInteract(PlayerInteractEvent event) {
        if (parent == null || parent.getContext() == null) return;
        System.out.println("Callking this event");
        if (!event.getPlayer().getUniqueId().equals(parent.getContext().getCaster().getUniqueId())) return;
        if (event.getAction() != Action.LEFT_CLICK_AIR && event.getAction() != Action.LEFT_CLICK_BLOCK) return;
        // (Optional additional checks may be performed here.)
        parent.triggered(this, event);
    }
}
