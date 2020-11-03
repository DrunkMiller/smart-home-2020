import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.SmartHomeConfiguration;
import ru.sbt.mipt.oop.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.SmartHomeReader;
import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorEventHandler;

import java.util.ArrayList;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class TestHallDoorEventHandler {
    private SmartHome smartHome;
    @BeforeEach
    void setUpSmartHome() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SmartHomeConfiguration.class);
        smartHome = context.getBean(SmartHome.class);
    }

    @Test
    void eventHallDoorClosed_checkSettingAllLightsStateOff() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        for (int i = 0; i < rooms.size(); i++) {
            if (i % 2 == 0) continue;
            List<Light> lights = new ArrayList<>(rooms.get(i).getLights());
            for (int j = 0; j < lights.size(); j++) {
                if (j % 2 == 0) continue;
                lights.get(j).turnOn();
            }
        }
        Door hallDoor = null;
        for (Room room : rooms) {
            if (room.getName().equals("hall")) {
                hallDoor = (Door) room.getDoors().toArray()[0];
                break;
            }
        }
        EventHandler hallDoorEvent = new HallDoorEventHandler();
        hallDoorEvent.handle(smartHome, new SensorEvent(DOOR_CLOSED, hallDoor.getId()));

        for (Room room : rooms) {
            for (Light light : room.getLights()) {
                assertEquals(false, light.isOn());
            }
        }
    }

}
