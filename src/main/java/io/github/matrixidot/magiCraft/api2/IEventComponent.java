package io.github.matrixidot.magiCraft.api2;

public interface IEventComponent<T> {
    void acceptEvent(T event);
}
