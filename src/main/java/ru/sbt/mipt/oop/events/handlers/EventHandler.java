package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventHandler {
    void handle(HomeComponent homeComponent, SensorEvent event);
}
