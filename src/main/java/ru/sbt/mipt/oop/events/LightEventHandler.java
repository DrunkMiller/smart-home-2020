package ru.sbt.mipt.oop.events;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import ru.sbt.mipt.oop.devices.*;

public class LightEventHandler implements EventHandler {
    @Override
    public void handle(SmartHome smartHome, SensorEvent event) {
        if (event.getType() == LIGHT_ON || event.getType() == LIGHT_OFF) {
            for (Room room : smartHome.getRooms()) {
                for (Light light : room.getLights()) {
                    if (light.getId().equals(event.getObjectId())) {
                        if (event.getType() == LIGHT_ON) {
                            light.turnOn();
                            System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned on.");
                        } else {
                            light.turnOff();
                            System.out.println("Light " + light.getId() + " in room " + room.getName() + " was turned off.");
                        }
                    }
                }
            }
        }
    }
}
