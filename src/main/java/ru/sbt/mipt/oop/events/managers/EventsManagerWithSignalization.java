package ru.sbt.mipt.oop.events.managers;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationActivateEventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationDeactivateEventHandler;
import ru.sbt.mipt.oop.signalization.Signalization;

import java.util.ArrayList;
import java.util.List;

public class EventsManagerWithSignalization extends EventsManagerDecorator {
    private Signalization signalization;
    private final List<EventHandler> signalizationHandlers;

    public EventsManagerWithSignalization(EventsManager decorated) {
        super(decorated);
        this.signalization = new Signalization();
        this.signalizationHandlers = new ArrayList<>();
    }

    @Override
    public void addHandler(EventHandler handler) {
        if (handler instanceof SignalizationActivateEventHandler || handler instanceof SignalizationDeactivateEventHandler) {
            signalizationHandlers.add(handler);
        }
        else {
            super.addHandler(handler);
        }
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (isSignalizationEvent(event)) {
            handleSignalizationEvent(event);
        }
        else if (signalization.isDeactivated()) {
            super.handleEvent(event);
        }
        else if (signalization.isActivated()) {
            signalization.alarm();
            sendAlarmMessage();
        }
        else {
            sendAlarmMessage();
        }
    }

    private void handleSignalizationEvent(SensorEvent event) {
        for (EventHandler handler : signalizationHandlers) {
            handler.handle(signalization, event);
        }
    }

    private boolean isSignalizationEvent(SensorEvent event) {
        return event.getType() == SensorEventType.SIGNALIZATION_ACTIVATE
                || event.getType() == SensorEventType.SIGNALIZATION_DEACTIVATE;
    }

    private void sendAlarmMessage() {
        System.out.println("Sending sms");
    }
}
