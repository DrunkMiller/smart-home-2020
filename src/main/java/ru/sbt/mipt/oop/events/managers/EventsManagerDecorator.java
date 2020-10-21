package ru.sbt.mipt.oop.events.managers;

import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.SensorEvent;

public class EventsManagerDecorator implements EventsManager {
    protected final EventsManager decorated;

    public EventsManagerDecorator(EventsManager decorated) {
        this.decorated = decorated;
    }

    @Override
    public void addHandler(EventHandler handler) {
        decorated.addHandler(handler);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        decorated.handleEvent(event);
    }
}
