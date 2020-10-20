package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class HallDoorEventHandler implements EventHandler {
    @Override
    public void handle(HomeComponent homeComponent, SensorEvent event) {
        if (event.getType() == DOOR_CLOSED) {
            homeComponent.execute(room -> {
                if (room instanceof Room && ((Room) room).getName().equals("hall")) {
                    homeComponent.execute(door -> {
                        if (door instanceof Door && ((Door) door).getId().equals(event.getObjectId())) {
                            homeComponent.execute(light -> {
                                if (light instanceof Light) {
                                    ((Light) light).turnOff();
                                }
                            });
                        }
                    });
                }
            });
        }
    }
}
