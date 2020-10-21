package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.signalization.Signalization;

public class SignalizationDeactivateEventHandler implements EventHandler {

    @Override
    public void handle(Object sender, SensorEvent event) {
        if (sender instanceof Signalization && event.getType() == SensorEventType.SIGNALIZATION_DEACTIVATE) {
            Signalization signalization = (Signalization) sender;
            signalization.deactivate((Integer) event.getEventArgs());
        }
    }
}
