import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.decorators.HomeComponentDecorator;
import ru.sbt.mipt.oop.events.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.components.Door;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.Room;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestDoorEventHandler {
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
    void eventDoorOpen_checkSettingDoorStateOpen() {
        List<HomeComponent> rooms = new ArrayList<>(getRoomsFromSmartHome(smartHome));
        Room room = (Room) rooms.get(0);
        Door door = (Door) room.getDoors().toArray()[0];
        door.close();
        EventHandler doorEvent = new DoorEventHandler();
        doorEvent.handle(smartHome, new SensorEvent(DOOR_OPEN, door.getId()));
        assertEquals(true, door.isOpen());
    }

    @Test
    void eventLightOff_checkSettingLightStateOff() {
        List<HomeComponent> rooms = new ArrayList<>(getRoomsFromSmartHome(smartHome));
        Room room = (Room) rooms.get(0);
        Door door = (Door) room.getDoors().toArray()[0];
        door.open();
        EventHandler doorEvent = new DoorEventHandler();
        doorEvent.handle(smartHome, new SensorEvent(DOOR_CLOSED, door.getId()));
        assertEquals(false, door.isOpen());
    }
}
