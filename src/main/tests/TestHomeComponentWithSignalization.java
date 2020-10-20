import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.Door;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.Light;
import ru.sbt.mipt.oop.components.Room;
import ru.sbt.mipt.oop.components.decorators.HomeComponentWithSignalization;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationEventHandler;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.signalization.SignalizationActivated;
import ru.sbt.mipt.oop.signalization.SignalizationAlarm;
import ru.sbt.mipt.oop.signalization.SignalizationDeactivated;
import ru.sbt.mipt.oop.signalization.SignalizationState;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestHomeComponentWithSignalization {
    private HomeComponent room;
    private Light light;
    @BeforeEach
    void setUpRoom() {
        light = new Light("1", false);
        room = new Room(
                Arrays.asList(light),
                new ArrayList<>(),
                "kitchen"
        );
    }
    @Test
    public void execute_transitionToAlarm_whenTryingAccessToInternalComponents() {
        HomeComponentWithSignalization componentWithSignalization = new HomeComponentWithSignalization("1", room);
        int signalizationCode = 123;
        componentWithSignalization.getSignalization().activate(signalizationCode);
        LightEventHandler lightEventHandler = new LightEventHandler();
        lightEventHandler.handle(componentWithSignalization, new SensorEvent(LIGHT_ON, light.getId()));

        boolean lightStateActual = light.isOn();
        boolean lightStateExpected = false;

        SignalizationState signalizationStateActual = componentWithSignalization.getSignalization().getSignalizationState();
        SignalizationState signalizationStateExpected = new SignalizationAlarm(componentWithSignalization.getSignalization(), signalizationCode);

        assertEquals(lightStateExpected, lightStateActual);
        assertEquals(signalizationStateActual.toString(), signalizationStateExpected.toString());
    }
}
