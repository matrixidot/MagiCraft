package io.github.matrixidot.magiCraft.api2;

import org.bukkit.event.Event;
import org.bukkit.event.Listener;
import org.bukkit.event.entity.EntityDamageByEntityEvent;
import org.bukkit.event.entity.ProjectileHitEvent;

import java.util.HashMap;
import java.util.Map;

public class GenericEventDispatcher {
    private static final HashMap<Class<? extends Event>, Map<Object, IEventComponent<? extends Event>>> callbacks = new HashMap<>();
    private static final HashMap<Class<? extends Event>, EventKeyExtractor<? extends Event>> keyExtractors = new HashMap<>();

    static {
        registerKeyExtractor(ProjectileHitEvent.class, ProjectileHitEvent::getEntity);
        registerKeyExtractor(EntityDamageByEntityEvent.class, EntityDamageByEntityEvent::getDamager);
    }

    public static <T extends Event> void registerKeyExtractor(Class<T> eventClass, EventKeyExtractor<T> extractor) {
        keyExtractors.put(eventClass, extractor);
    }

    public static <T extends Event> void addCallback(Class<T> eventClass, Object key, IEventComponent<T> component) {
        Map<Object, IEventComponent<? extends Event>> map = callbacks.computeIfAbsent(eventClass, k -> new HashMap<>());
        map.put(key, component);
    }

    @SuppressWarnings("unchecked")
    public static <T extends Event> void dispatchEvent(T event) {
        Map<Object, IEventComponent<? extends Event>> map = callbacks.get(event.getClass());
        if (map == null) return;

        EventKeyExtractor<T> extractor = (EventKeyExtractor<T>) keyExtractors.get(event.getClass());
        if (extractor == null) return;

        Object key = extractor.extractKey(event);
        IEventComponent<T> component = (IEventComponent<T>) map.remove(key);
        if (component != null) component.acceptEvent(event);
    }
}
