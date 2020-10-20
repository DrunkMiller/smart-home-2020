package ru.sbt.mipt.oop.events.receivers;

import ru.sbt.mipt.oop.events.SensorEvent;

public interface EventsReceiver {
    SensorEvent getNextSensorEvent();
}
