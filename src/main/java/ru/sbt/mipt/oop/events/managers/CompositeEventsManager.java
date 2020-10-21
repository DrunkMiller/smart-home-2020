package ru.sbt.mipt.oop.events.managers;

import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.util.ArrayList;
import java.util.List;

public class CompositeEventsManager implements EventsManager {
    private final List<EventHandler> handlers;
    private final SmartHome smartHome;

    public CompositeEventsManager(SmartHome smartHome) {
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
