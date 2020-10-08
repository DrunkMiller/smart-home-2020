package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class HallDoorEventHandler implements EventHandler {
    private Room hallRoom = null;
    private Door hallDoor = null;
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == DOOR_CLOSED) {
            smartHome.execute(component -> {
                if (component instanceof Room && ((Room) component).getName().equals("hall")) {
                    hallRoom = (Room) component;
                }
            });
            if (hallRoom != null) {
                hallRoom.execute(component -> {
                    if (component instanceof Door && ((Door) component).getId().equals(event.getObjectId())) {
                        hallDoor = (Door) component;
                    }
                });
            }
            if (hallDoor != null) {
                smartHome.execute(component -> {
                    if (component instanceof Light) {
                        ((Light) component).turnOff();
                    }
                });
            }
            hallRoom = null;
            hallDoor = null;
        }
    }
}
