import  static org.junit.jupiter.api.Assertions.*;
import static ru.sbt.mipt.oop.events.SensorEventType.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.SmartHomeReader;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;

import java.util.ArrayList;
import java.util.List;

public class TestLightEventHandler {
    private SmartHome smartHome;
    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        smartHome = reader.read();
    }

    @Test
    void eventLightOn_checkSettingLightStateOn() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        Light light = (Light) rooms.get(0).getLights().toArray()[0];
        light.turnOff();
        EventHandler lightEvent = new LightEventHandler();
        lightEvent.handle(smartHome, new SensorEvent(LIGHT_ON, light.getId()));
        assertEquals(true, light.isOn());
    }

    @Test
    void eventLightOff_checkSettingLightStateOff() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        Light light = (Light) rooms.get(0).getLights().toArray()[0];
        light.turnOn();
        EventHandler lightEvent = new LightEventHandler();
        lightEvent.handle(smartHome, new SensorEvent(LIGHT_OFF, light.getId()));
        assertEquals(false, light.isOn());
    }
}
