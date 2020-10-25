package ru.sbt.mipt.oop.events;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;

public class LightEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
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
