import org.junit.Test;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationActivateEventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationDeactivateEventHandler;
import ru.sbt.mipt.oop.signalization.Signalization;

import static org.junit.jupiter.api.Assertions.assertTrue;
import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class TestSignalizationDeactivateEventHandler {
    @Test
    public void checkTransitionInDeactivatedFromActivated_whenHandleEventSignalizationDeactivate() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        EventHandler signalizationActivateEvent = new SignalizationActivateEventHandler();
        signalizationActivateEvent.handle(signalization, new SensorEvent(SIGNALIZATION_ACTIVATE, " ", signalizationCode));
        EventHandler signalizationDeactivateEvent = new SignalizationDeactivateEventHandler();
        signalizationDeactivateEvent.handle(signalization, new SensorEvent(SIGNALIZATION_DEACTIVATE, " ", signalizationCode));

        assertTrue(signalization.isDeactivated());
    }

    @Test
    public void checkTransitionInDeactivatedFromAlarm_whenHandleEventSignalizationDeactivate() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        signalization.alarm();

        EventHandler signalizationDeactivateEvent = new SignalizationDeactivateEventHandler();
        signalizationDeactivateEvent.handle(signalization, new SensorEvent(SIGNALIZATION_DEACTIVATE, " ", signalizationCode));

        assertTrue(signalization.isDeactivated());
    }
}
