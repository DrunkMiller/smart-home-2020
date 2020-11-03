import  static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.SmartHomeReader;
import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;

import java.util.ArrayList;
import java.util.Collection;

public class TestSmartHome {
    private SmartHome smartHome;
    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        smartHome = reader.read();
    }

    @Test
    void iterRooms_checkCorrectPassageOnRooms() {
        Collection<Room> actualRooms = smartHome.getRooms();
        Collection<Room> expectedRooms = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Room) {
                expectedRooms.add((Room) component);
            }
        });
        assertIterableEquals(expectedRooms, actualRooms);
    }

    @Test
    void iterDoors_checkCorrectPassageOnDoors() {
        Collection<Door> actualDoors = new ArrayList<>();
        for (Room room : smartHome.getRooms()) {
            actualDoors.addAll(room.getDoors());
        }
        Collection<Door> expectedDoors = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Door) {
                expectedDoors.add((Door) component);
            }
        });
        assertIterableEquals(expectedDoors, actualDoors);
    }

    @Test
    void iterLights_checkCorrectPassageOnLights() {
        Collection<Light> actualLights = new ArrayList<>();
        for (Room room : smartHome.getRooms()) {
            actualLights.addAll(room.getLights());
        }
        Collection<Light> expectedLights = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Light) {
                expectedLights.add((Light) component);
            }
        });
        assertIterableEquals(expectedLights, actualLights);
    }
}
