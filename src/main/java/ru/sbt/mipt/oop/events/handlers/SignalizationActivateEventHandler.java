package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.signalization.Signalization;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class SignalizationActivateEventHandler implements EventHandler {
    @Override
    public void handle(Object sender, SensorEvent event) {
        if (sender instanceof Signalization && event.getType() == SIGNALIZATION_ACTIVATE) {
            Signalization signalization = (Signalization) sender;
            signalization.activate((Integer) event.getEventArgs());
        }
    }
}
