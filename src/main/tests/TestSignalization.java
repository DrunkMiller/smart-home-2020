import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.signalization.*;

import static org.junit.jupiter.api.Assertions.*;

class TestSignalization {

    @Test
    void createSignalization_checkCorrectSignalizationState_whenCreating() {
        Signalization signalization = new Signalization();

        assertTrue(signalization.isDeactivated());
        assertFalse(signalization.isActivated());
        assertFalse(signalization.isAlarm());
    }

    @Test
    void activate_checkCorrectSignalizationState_whenSignalizationActivated() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);

        assertTrue(signalization.isActivated());
        assertFalse(signalization.isDeactivated());
        assertFalse(signalization.isAlarm());
    }

    @Test
    void deactivate_checkCorrectSignalizationState_whenSignalizationDeactivated() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        signalization.deactivate(signalizationCode);

        assertTrue(signalization.isDeactivated());
        assertFalse(signalization.isActivated());
        assertFalse(signalization.isAlarm());
    }

    @Test
    void alarm_checkCorrectSignalizationState_whenSignalizationInAlarm() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        signalization.alarm();

        assertTrue(signalization.isAlarm());
        assertFalse(signalization.isActivated());
        assertFalse(signalization.isDeactivated());
    }

    @Test
    void signalization_checkCorrectSignalizationWorkingCycle() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;

        signalization.alarm();
        signalization.deactivate(signalizationCode);
        assertTrue(signalization.isDeactivated());

        signalization.activate(signalizationCode);
        assertTrue(signalization.isActivated());

        signalization.alarm();
        assertTrue(signalization.isAlarm());

        signalization.deactivate(signalizationCode);
        assertTrue(signalization.isDeactivated());
    }
}