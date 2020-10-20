import  static org.junit.jupiter.api.Assertions.*;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.decorators.HomeComponentDecorator;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.Light;
import ru.sbt.mipt.oop.components.Room;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;

import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;
import java.util.List;

public class TestLightEventHandler {
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
    void eventLightOn_checkSettingLightStateOn() {
        List<HomeComponent> rooms = new ArrayList<>(getRoomsFromSmartHome(smartHome));
        Room room = (Room) rooms.get(0);
        Light light = (Light) room.getLights().toArray()[0];
        light.turnOff();
        EventHandler lightEvent = new LightEventHandler();
        lightEvent.handle(smartHome, new SensorEvent(LIGHT_ON, light.getId()));
        assertEquals(true, light.isOn());
    }

    @Test
    void eventLightOff_checkSettingLightStateOff() {
        List<HomeComponent> rooms = new ArrayList<>(getRoomsFromSmartHome(smartHome));
        Room room = (Room) rooms.get(0);
        Light light = (Light) room.getLights().toArray()[0];
        light.turnOn();
        EventHandler lightEvent = new LightEventHandler();
        lightEvent.handle(smartHome, new SensorEvent(LIGHT_OFF, light.getId()));
        assertEquals(false, light.isOn());
    }
}
