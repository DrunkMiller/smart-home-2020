package ru.sbt.mipt.oop.events;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;

public class DoorEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            smartHome.execute(component -> {
                if (component instanceof Door && ((Door) component).getId().equals(event.getObjectId())) {
                    if (event.getType() == DOOR_OPEN) {
                        ((Door) component).open();
                    }
                    else {
                        ((Door) component).close();
                    }
                }
            });
        }
    }
}