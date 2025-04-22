package io.github.matrixidot.magiCraft.api;

import com.mojang.datafixers.util.Pair;
import org.bukkit.event.Event;
import org.bukkit.event.EventPriority;
import org.bukkit.event.Listener;
import org.bukkit.plugin.EventExecutor;
import org.bukkit.plugin.Plugin;
import java.util.concurrent.CompletableFuture;
import java.util.function.Consumer;
import java.util.function.Predicate;

/*
 * Fluent builder for chaining deferred event workflows without using @EventHandler methods.
 * Supports mapping events to context values, awaiting further events with access to previous context,
 * and consuming events or contexts at each step.
 */
public class EventChain<T extends Event> {
    private final Plugin plugin;
    private final CompletableFuture<T> future;

    private EventChain(Plugin plugin, CompletableFuture<T> future) {
        this.plugin = plugin;
        this.future = future;
    }

    /*
     * Start the chain by waiting for an event of type E, filtered by the given predicate.
     */
    public static <E extends Event> EventChain<E> begin(Plugin plugin, Class<E> eventType, Predicate<E> filter) {
        CompletableFuture<E> fut = waitForEvent(plugin, eventType, filter);
        return new EventChain<>(plugin, fut);
    }

    private static <E extends Event> CompletableFuture<E> waitForEvent(Plugin plugin, Class<E> eventType, Predicate<E> filter) {
        CompletableFuture<E> future = new CompletableFuture<>();
        Listener listener = new Listener() {};

        EventExecutor executor = (l, event) -> {
            try {
                E casted = (E) event;
                if (!filter.test(casted)) return;
                future.complete(casted);
                event.getHandlers().unregister(listener);
            } catch (ClassCastException ignored) {}
        };

        plugin.getServer().getPluginManager().registerEvent(eventType, listener, EventPriority.NORMAL, executor, plugin);

        future.whenComplete((res, err) -> {
            res.getHandlers().unregister(listener);
        });

        return future;
    }

    /*
     * Run a side-effect when the current event fires (matching the chain so far).
     * Returns the same chain for further chaining.
     */
    public EventChain<T> eventFired(Consumer<T> action) {
        future.thenAccept(action);
        return this;
    }

    /*
     * Handle exceptions that occur anywhere in the chain.
     */
    public EventChain<T> except(Consumer<Throwable> errorHandler) {
        future.exceptionally(ex -> {
            errorHandler.accept(ex);
            return null;
        });
        return this;
    }

    /**
     * Expose the underlying future if needed.
     */
    public CompletableFuture<T> toFuture() {
        return future;
    }
}


