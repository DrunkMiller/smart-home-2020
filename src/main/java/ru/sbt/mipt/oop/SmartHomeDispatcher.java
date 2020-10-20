package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.events.receivers.EventsReceiver;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.SensorEvent;

public class SmartHomeDispatcher {
    private final HomeComponent homeComponent;
    private final EventsManager eventsManager;
    private final EventsReceiver eventReceiver;

    public SmartHomeDispatcher(HomeComponent homeComponent, EventsManager eventsManager, EventsReceiver eventReceiver) {
        this.homeComponent = homeComponent;
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
