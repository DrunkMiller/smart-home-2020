package ru.sbt.mipt.oop.events;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;

public class DoorEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == DOOR_OPEN || event.getType() == DOOR_CLOSED) {
            for (Room room : smartHome.getRooms()) {
                for (Door door : room.getDoors()) {
                    if (door.getId().equals(event.getObjectId())) {
                        if (event.getType() == DOOR_OPEN) {
                            door.open();
                            System.out.println("Door " + door.getId() + " in room " + room.getName() + " was opened.");
                        } else {
                            door.close();
                            System.out.println("Door " + door.getId() + " in room " + room.getName() + " was closed.");
                        }
                    }
                }
            }
        }
    }
}
