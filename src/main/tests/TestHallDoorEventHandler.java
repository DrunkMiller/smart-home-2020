import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.decorators.HomeComponentDecorator;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.DOOR_CLOSED;

public class TestHallDoorEventHandler {
    private HomeComponent smartHome;

    private Collection<HomeComponent> getRoomsFromSmartHome(HomeComponent component) {
        try {
            SmartHome smartHome = null;
            if (component instanceof HomeComponentDecorator) {
                smartHome = (SmartHome) ((HomeComponentDecorator) component).getBaseComponent();
            }
            else {
                smartHome = (SmartHome) component;
            }
            Field field = smartHome.getClass().getDeclaredField("rooms");
            field.setAccessible(true);
            return (Collection<HomeComponent>) field.get(smartHome);
        }
        catch (Exception exception) {
            return new ArrayList<>();
        }
    }

    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home.js");
        smartHome = reader.read();
    }

    @Test
    void eventHallDoorClosed_checkSettingAllLightsStateOff() {
        List<HomeComponent> rooms = new ArrayList<>(getRoomsFromSmartHome(smartHome));
        for (int i = 0; i < rooms.size(); i++) {
            Room room = (Room) rooms.get(i);
            if (i % 2 == 0) continue;
            List<HomeComponent> lights = new ArrayList<>(room.getLights());
            for (int j = 0; j < lights.size(); j++) {
                Light light = (Light) lights.get(j);
                if (j % 2 == 0) continue;
                light.turnOn();
            }
        }
        Door hallDoor = null;
        for (HomeComponent component : rooms) {
            Room room = (Room) component;
            if (room.getName().equals("hall")) {
                hallDoor = (Door) room.getDoors().toArray()[0];
                break;
            }
        }
        EventHandler hallDoorEvent = new HallDoorEventHandler();
        hallDoorEvent.handle(smartHome, new SensorEvent(DOOR_CLOSED, hallDoor.getId()));

        for (HomeComponent component : rooms) {
            Room room = (Room) component;
            for (HomeComponent lightComponent : room.getLights()) {
                Light light = (Light) lightComponent;
                assertEquals(false, light.isOn());
            }
        }
    }

}
