package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.devices.SmartHome;

import java.util.ArrayList;
import java.util.List;

public class SimpleEventsManager implements EventsManager {
    private final List<EventHandler> handlers;
    private final SmartHome smartHome;

    public SimpleEventsManager(SmartHome smartHome) {
        this.smartHome = smartHome;
        this.handlers = new ArrayList<>();
    }

    @Override
    public void addHandler(EventHandler handler) {
        handlers.add(handler);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        for (EventHandler handler : handlers) {
            handler.handle(smartHome, event);
        }
    }
}
