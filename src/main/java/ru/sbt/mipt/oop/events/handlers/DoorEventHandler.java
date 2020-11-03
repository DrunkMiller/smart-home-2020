package ru.sbt.mipt.oop.events.handlers;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;
import ru.sbt.mipt.oop.events.SensorEvent;

public class DoorEventHandler implements EventHandler {
    @Override
    public void handle(Object sender, SensorEvent event) {
        if (sender instanceof SmartHome && event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            SmartHome smartHome = (SmartHome) sender;
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
