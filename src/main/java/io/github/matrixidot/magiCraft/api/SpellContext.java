package io.github.matrixidot.magiCraft.api;

import org.bukkit.entity.Player;
import org.bukkit.plugin.java.JavaPlugin;
import java.util.HashMap;
import java.util.Map;

public class SpellContext {
    private final Player caster;
    private final JavaPlugin plugin;
    private final Map<SpellPayloadKey, Object> payload = new HashMap<>();

    public SpellContext(Player caster, JavaPlugin plugin) {
        this.caster = caster;
        this.plugin = plugin;
    }

    public Player getCaster() {
        return caster;
    }

    public JavaPlugin getPlugin() {
        return plugin;
    }

    public Map<SpellPayloadKey, Object> getPayload() {
        return payload;
    }
}
