package ru.sbt.mipt.oop.events;

public interface EventsReceiver {
    SensorEvent getNextSensorEvent();
}
