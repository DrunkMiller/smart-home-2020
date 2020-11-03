package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.EventsReceiver;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.SensorEvent;

public class SmartHomeDispatcher {
    private final SmartHome smartHome;
    private final EventsManager eventsManager;
    private final EventsReceiver eventReceiver;

    public SmartHomeDispatcher(SmartHome smartHome, EventsManager eventsManager, EventsReceiver eventReceiver) {
        this.smartHome = smartHome;
        this.eventsManager = eventsManager;
        this.eventReceiver = eventReceiver;
    }

    public void start() {
        SensorEvent event = eventReceiver.getNextSensorEvent();
        while (event != null) {
            eventsManager.handleEvent(event);
            event = eventReceiver.getNextSensorEvent();
        }
    }
}
