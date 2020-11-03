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

    private Signalization getSignalization(EventsManager manager) {
        try {
            Field field = manager.getClass().getDeclaredField("signalization");
            field.setAccessible(true);
            return (Signalization) field.get(manager);
        }
        catch (Exception ignored) {
            return null;
        }
    }

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
    public void handle_checkTransitionInActivatedFromDeactivated_whenHandleEventSignalizationActivate() {
        EventsManager manager = new EventsManagerWithSignalization(new CompositeEventsManager(smartHome, Arrays.asList(new LightEventHandler())));
        int signalizationCode = 123;
        Signalization signalization = getSignalization(manager);

        manager.handleEvent(new SensorEvent(SIGNALIZATION_ACTIVATE, "", signalizationCode));

        assertTrue(signalization.isActivated());
    }

    @Test
    public void handle_checkTransitionInDeactivatedFromActivated_whenHandleEventSignalizationDeactivate() throws NoSuchFieldException, IllegalAccessException {
        EventsManager manager = new EventsManagerWithSignalization(new CompositeEventsManager(smartHome, Arrays.asList(new LightEventHandler())));
        int signalizationCode = 123;
        Signalization signalization = getSignalization(manager);

        manager.handleEvent(new SensorEvent(SIGNALIZATION_ACTIVATE, "", signalizationCode));
        manager.handleEvent(new SensorEvent(SIGNALIZATION_DEACTIVATE, "", signalizationCode));

        assertTrue(signalization.isDeactivated());
    }

    @Test
    public void handle_checkTransitionInDeactivatedFromAlarm_whenHandleEventSignalizationDeactivate() {
        EventsManager manager = new EventsManagerWithSignalization(new CompositeEventsManager(smartHome, Arrays.asList(new LightEventHandler())));
        int signalizationCode = 123;
        Signalization signalization = getSignalization(manager);

        manager.handleEvent(new SensorEvent(SIGNALIZATION_ACTIVATE, "", signalizationCode));
        manager.handleEvent(new SensorEvent(LIGHT_ON, "1"));
        manager.handleEvent(new SensorEvent(SIGNALIZATION_DEACTIVATE, "", signalizationCode));
        
        assertTrue(signalization.isDeactivated());
    }

    @Test
    public void handle_transitionToAlarm_whenReceivedEvent() throws NoSuchFieldException, IllegalAccessException {
        EventsManager manager = new EventsManagerWithSignalization(new CompositeEventsManager(smartHome, Arrays.asList(new LightEventHandler())));
        int signalizationCode = 123;
        Signalization signalization = getSignalization(manager);

        manager.handleEvent(new SensorEvent(SIGNALIZATION_ACTIVATE, "", signalizationCode));
        manager.handleEvent(new SensorEvent(LIGHT_ON, "1"));

        assertTrue(signalization.isAlarm());
    }
}
