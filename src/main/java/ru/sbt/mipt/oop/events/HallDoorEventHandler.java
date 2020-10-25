package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class HallDoorEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        Room hallRoom = null;
        Door hallDoor = null;
        if (event.getType() == DOOR_CLOSED) {
            smartHome.execute(room -> {
                if (room instanceof Room && ((Room) room).getName().equals("hall")) {
                    smartHome.execute(door -> {
                        if (door instanceof Door && ((Door) door).getId().equals(event.getObjectId())) {
                            smartHome.execute(light -> {
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
