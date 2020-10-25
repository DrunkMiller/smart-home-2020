package ru.sbt.mipt.oop.events;

public interface EventsManager {
    void addHandler(EventHandler handler);
    void handleEvent(SensorEvent event);
}
