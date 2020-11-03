package ru.sbt.mipt.oop.events.handlers;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LightEventHandler implements EventHandler {
    @Override
    public void handle(Object sender, SensorEvent event) {
        if (sender instanceof SmartHome && event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            SmartHome smartHome = (SmartHome) sender;
            smartHome.execute(component -> {
                if (component instanceof Light && ((Light) component).getId().equals(event.getObjectId())) {
                    if (event.getType() == LIGHT_ON) {
                        ((Light) component).turnOn();
                    }
                    else {
                        ((Light) component).turnOff();
                    }
                }
            });
        }
    }
}
