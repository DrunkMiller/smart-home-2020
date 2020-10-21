import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.*;
import ru.sbt.mipt.oop.events.managers.CompositeEventsManager;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.managers.EventsManagerWithSignalization;
import ru.sbt.mipt.oop.signalization.*;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Arrays;
import ru.sbt.mipt.oop.devices.*;
import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestEventsManagerWithSignalization {
    private SmartHome smartHome;
    private Light light;
    @BeforeEach
    void setUpSmartHome() {
        light = new Light("1", false);
        Room room = new Room(
                Arrays.asList(light),
                new ArrayList<>(),
                "kitchen"
        );
        smartHome = new SmartHome(Arrays.asList(room));
    }

    @Test
    public void handle_transitionToAlarm_whenReceivedEvent() throws NoSuchFieldException, IllegalAccessException {
        EventsManager manager = new EventsManagerWithSignalization(new CompositeEventsManager(smartHome));
        manager.addHandler(new SignalizationActivateEventHandler());
        manager.addHandler(new LightEventHandler());
        int signalizationCode = 123;

        Field field = manager.getClass().getDeclaredField("signalization");
        field.setAccessible(true);
        Signalization signalization = (Signalization) field.get(manager);

        manager.handleEvent(new SensorEvent(SIGNALIZATION_ACTIVATE, "", signalizationCode));
        manager.handleEvent(new SensorEvent(LIGHT_ON, "1"));


        assertTrue(signalization.isAlarm());
    }
}
