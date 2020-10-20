package ru.sbt.mipt.oop.events.handlers;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.events.SensorEvent;

public class DoorEventHandler implements EventHandler {
    @Override
    public void handle(HomeComponent homeComponent, SensorEvent event) {
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            homeComponent.execute(component -> {
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
