import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.signalization.*;
import static org.junit.jupiter.api.Assertions.assertEquals;

class TestSignalization {



    @Test
    void createSignalization_checkCorrectSignalizationState_whenCreating() {
        Signalization signalization = new Signalization();
        SignalizationState actual = signalization.getSignalizationState();
        SignalizationState expected = new SignalizationDeactivated(signalization);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void activate_checkCorrectSignalizationState_whenSignalizationActivated() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        SignalizationState actual = signalization.getSignalizationState();
        SignalizationState expected = new SignalizationActivated(signalization, 123);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void deactivate_checkCorrectSignalizationState_whenSignalizationDeactivated() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        signalization.deactivate(signalizationCode);
        SignalizationState actual = signalization.getSignalizationState();
        SignalizationState expected = new SignalizationDeactivated(signalization);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void alarm_checkCorrectSignalizationState_whenSignalizationInAlarm() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        signalization.alarm();
        SignalizationState actual = signalization.getSignalizationState();
        SignalizationState expected = new SignalizationAlarm(signalization, signalizationCode);
        assertEquals(expected.toString(), actual.toString());
    }

    @Test
    void signalization_checkCorrectSignalizationWorkingCycle() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;

        signalization.alarm();
        signalization.deactivate(signalizationCode);
        SignalizationState actualWhenDeactivated = signalization.getSignalizationState();
        SignalizationState expectedWhenDeactivated = new SignalizationDeactivated(signalization);
        assertEquals(actualWhenDeactivated.toString(), expectedWhenDeactivated.toString());

        signalization.activate(signalizationCode);
        SignalizationState actualWhenActivated = signalization.getSignalizationState();
        SignalizationState expectedWhenActivated = new SignalizationActivated(signalization, signalizationCode);
        assertEquals(actualWhenActivated.toString(), expectedWhenActivated.toString());

        signalization.alarm();
        SignalizationState actualWhenInAlarm = signalization.getSignalizationState();
        SignalizationState expectedWhenInAlarm = new SignalizationAlarm(signalization, signalizationCode);
        assertEquals(actualWhenInAlarm.toString(), expectedWhenInAlarm.toString());

        signalization.deactivate(signalizationCode);
        SignalizationState actualWhenDeactivatedAfterAlarm = signalization.getSignalizationState();
        SignalizationState expectedWhenDeactivatedAfterAlarm = new SignalizationDeactivated(signalization);
        assertEquals(actualWhenDeactivatedAfterAlarm.toString(), expectedWhenDeactivatedAfterAlarm.toString());
    }
}