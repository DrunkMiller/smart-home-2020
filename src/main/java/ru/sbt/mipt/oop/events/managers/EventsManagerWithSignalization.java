package ru.sbt.mipt.oop.events.managers;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.signalization.Signalization;

import static ru.sbt.mipt.oop.events.SensorEventType.*;


public class EventsManagerWithSignalization implements EventsManager {
    private final EventsManager decorated;
    private Signalization signalization;

    public EventsManagerWithSignalization(EventsManager decorated) {
        this.decorated = decorated;
        this.signalization = new Signalization();
    }

    @Override
    public void addHandler(EventHandler handler) {
        decorated.addHandler(handler);
    }

    @Override
    public void handleEvent(SensorEvent event) {
        if (isSignalizationEvent(event)) {
            handleSignalizationEvent(event);
        }
        else if (signalization.isDeactivated()) {
            decorated.handleEvent(event);
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
        if (event.getType() == SIGNALIZATION_ACTIVATE) {
            signalization.activate((Integer) event.getEventArgs());
        }
        else if (event.getType() == SIGNALIZATION_DEACTIVATE) {
            signalization.deactivate((Integer) event.getEventArgs());
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
