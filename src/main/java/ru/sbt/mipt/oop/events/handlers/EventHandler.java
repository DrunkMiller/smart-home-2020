package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventHandler {
    void handle(Object sender, SensorEvent event);
}
