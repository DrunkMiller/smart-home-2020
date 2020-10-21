import org.junit.Test;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationActivateEventHandler;
import ru.sbt.mipt.oop.signalization.Signalization;
import static ru.sbt.mipt.oop.events.SensorEventType.SIGNALIZATION_ACTIVATE;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class TestSignalizationActivateEventHandler {

    @Test
    public void checkTransitionInActivatedFromDeactivated_whenHandleEventSignalizationActivate() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        EventHandler signalizationEvent = new SignalizationActivateEventHandler();
        signalizationEvent.handle(signalization, new SensorEvent(SIGNALIZATION_ACTIVATE, " ", signalizationCode));
        assertTrue(signalization.isActivated());
    }
}
