package io.github.matrixidot.magiCraft.api2;

import org.bukkit.event.Event;

public interface EventKeyExtractor<T extends Event> {

    Object extractKey(T event);
}
