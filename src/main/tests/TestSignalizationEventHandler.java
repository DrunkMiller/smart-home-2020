import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.decorators.HomeComponentWithSignalization;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationEventHandler;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.signalization.SignalizationActivated;
import ru.sbt.mipt.oop.signalization.SignalizationDeactivated;
import ru.sbt.mipt.oop.signalization.SignalizationState;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestSignalizationEventHandler {
    private HomeComponent smartHome;
    private HomeComponentWithSignalization smartHomeSignalizationDecorator;
    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home.js");
        smartHome = reader.read();
        smartHomeSignalizationDecorator = ((HomeComponentWithSignalization) smartHome);
    }

    @Test
    public void eventSignalizationActivate_checkTransitionInStateActivated() {
        int signalizationCode = 123;
        EventHandler signalizationEvent = new SignalizationEventHandler();
        signalizationEvent.handle(smartHome, new SensorEvent(SIGNALIZATION_ACTIVATE, smartHomeSignalizationDecorator.getId(), signalizationCode));
        SignalizationState actual = smartHomeSignalizationDecorator.getSignalization().getSignalizationState();
        SignalizationState expected = new SignalizationActivated(smartHomeSignalizationDecorator.getSignalization(), signalizationCode);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void eventSignalizationDeactivate_checkTransitionInStateDeactivateWhenSignalizationActivated() {
        int signalizationCode = 123;
        EventHandler signalizationEvent = new SignalizationEventHandler();
        signalizationEvent.handle(smartHome, new SensorEvent(SIGNALIZATION_ACTIVATE, smartHomeSignalizationDecorator.getId(), signalizationCode));
        signalizationEvent.handle(smartHome, new SensorEvent(SIGNALIZATION_DEACTIVATE, smartHomeSignalizationDecorator.getId(), signalizationCode));

        SignalizationState actual = smartHomeSignalizationDecorator.getSignalization().getSignalizationState();
        SignalizationState expected = new SignalizationDeactivated(smartHomeSignalizationDecorator.getSignalization());
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    public void eventSignalizationDeactivate_checkTransitionInStateDeactivateWhenSignalizationInAlarm() {
        int signalizationCode = 123;
        EventHandler signalizationEvent = new SignalizationEventHandler();
        EventHandler lightEvent = new LightEventHandler();
        signalizationEvent.handle(smartHome, new SensorEvent(SIGNALIZATION_ACTIVATE, smartHomeSignalizationDecorator.getId(), signalizationCode));
        lightEvent.handle(smartHome, new SensorEvent(LIGHT_ON, "1"));
        signalizationEvent.handle(smartHome, new SensorEvent(SIGNALIZATION_DEACTIVATE, smartHomeSignalizationDecorator.getId(), signalizationCode));

        SignalizationState actual = smartHomeSignalizationDecorator.getSignalization().getSignalizationState();
        SignalizationState expected = new SignalizationDeactivated(smartHomeSignalizationDecorator.getSignalization());
        assertEquals(expected.toString(), actual.toString());
    }
}
