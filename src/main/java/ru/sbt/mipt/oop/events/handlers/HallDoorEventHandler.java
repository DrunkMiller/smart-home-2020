package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class HallDoorEventHandler implements EventHandler {
    @Override
    public void handle(Object sender, SensorEvent event) {
        if (sender instanceof SmartHome && event.getType() == DOOR_CLOSED) {
            SmartHome smartHome = (SmartHome) sender;
            Room hallRoom = null;
            Door hallDoor = null;
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
