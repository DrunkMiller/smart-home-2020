package ru.sbt.mipt.oop.events;

import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;

import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_OPEN;

public class HallDoorEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == DOOR_CLOSED) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId()) && room.getName().equals("hall")) {
                        for (Room homeRoom : smartHome.getRooms()) {
                            for (Light light : homeRoom.getLights()) {
                                light.turnOff();
                            }
                        }
                    }
                }
            }
        }
    }
}
