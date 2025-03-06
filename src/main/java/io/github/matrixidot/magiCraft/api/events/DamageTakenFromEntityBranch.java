package io.github.matrixidot.magiCraft.api.events;

import com.google.gson.JsonObject;
import io.github.matrixidot.magiCraft.api.AbstractEventBranch;
import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellNode;
import io.github.matrixidot.magiCraft.api.SpellPayloadKey;
import io.github.matrixidot.magiCraft.api.control.ForNode;
import org.bukkit.Location;
import org.bukkit.entity.Entity;
import org.bukkit.entity.Player;
import org.bukkit.event.EventHandler;
import org.bukkit.event.entity.EntityDamageByEntityEvent;

public class DamageTakenFromEntityBranch extends AbstractEventBranch<EntityDamageByEntityEvent> {

    public DamageTakenFromEntityBranch(SpellNode childNode) {
        super(childNode);
    }

    @Override
    public Class<EntityDamageByEntityEvent> getEventClass() {
        return EntityDamageByEntityEvent.class;
    }

    @EventHandler
    public void onEntityDamage(EntityDamageByEntityEvent event) {
        if (parent == null || parent.getContext() == null) return;
        if (!(event.getEntity() instanceof Player attacked)) return;
        SpellContext ctx = parent.getContext();
        if (!attacked.getUniqueId().equals(ctx.getCaster().getUniqueId())) return;

        Entity damager = event.getDamager();
        if (damager != null) {
            Location loc = damager.getLocation();
            ctx.getPayload().put(SpellPayloadKey.ACTION_LOCATION, loc);
        }
        unregister();
        parent.triggered(this, event);
    }
}
