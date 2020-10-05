package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.devices.SmartHome;

public interface EventHandler {
    void handle(SmartHome smartHome, SensorEvent event);
}
