package ru.sbt.mipt.oop.events.managers;

import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventsManager {
    void addHandler(EventHandler handler);
    void handleEvent(SensorEvent event);
}
