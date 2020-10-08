package ru.sbt.mipt.oop.events;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.SmartHomeReader;
import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestDoorEventHandler {
    private SmartHome smartHome;
    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        smartHome = reader.read();
    }

    @Test
    void eventDoorOpen_checkSettingDoorStateOpen() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        Door door = (Door) rooms.get(0).getDoors().toArray()[0];
        door.close();
        EventHandler doorEvent = new DoorEventHandler();
        doorEvent.handle(smartHome, new SensorEvent(DOOR_OPEN, door.getId()));
        assertEquals(true, door.isOpen());
    }

    @Test
    void eventLightOff_checkSettingLightStateOff() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        Door door = (Door) rooms.get(0).getDoors().toArray()[0];
        door.open();
        EventHandler doorEvent = new DoorEventHandler();
        doorEvent.handle(smartHome, new SensorEvent(DOOR_CLOSED, door.getId()));
        assertEquals(false, door.isOpen());
    }
}
