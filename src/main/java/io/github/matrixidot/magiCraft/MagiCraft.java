package io.github.matrixidot.magiCraft;

import io.github.matrixidot.magiCraft.api.SpellContext;
import io.github.matrixidot.magiCraft.api.SpellPayloadKey;
import org.bukkit.Bukkit;
import org.bukkit.Material;
import org.bukkit.event.EventHandler;
import org.bukkit.event.Listener;
import org.bukkit.event.block.Action;
import org.bukkit.event.player.PlayerInteractEvent;
import org.bukkit.plugin.java.JavaPlugin;

public class MagiCraft extends JavaPlugin implements Listener {
    @Override
    public void onEnable() {
        super.onEnable();
        Bukkit.getPluginManager().registerEvents(this, this);
    }

    @Override
    public void onDisable() {
        super.onDisable();
    }

    @EventHandler
    public void onInteract(PlayerInteractEvent event) {
        if (event.getAction() != Action.RIGHT_CLICK_AIR) return;
        if (event.getItem().getType() != Material.BOOK) return;
        ExampleCircuit.createSecondExampleCircuit().execute(new SpellContext(event.getPlayer(), this));
    }
}
