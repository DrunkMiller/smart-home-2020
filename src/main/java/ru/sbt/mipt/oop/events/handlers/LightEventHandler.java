package ru.sbt.mipt.oop.events.handlers;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.events.SensorEvent;

public class LightEventHandler implements EventHandler {
    @Override
    public void handle(HomeComponent homeComponent, SensorEvent event) {
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            homeComponent.execute(component -> {
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
