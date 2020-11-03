import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.context.ApplicationContext;
import org.springframework.context.annotation.AnnotationConfigApplicationContext;
import ru.sbt.mipt.oop.SmartHomeConfiguration;
import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.remote.commands.*;
import ru.sbt.mipt.oop.signalization.Signalization;

import java.util.ArrayList;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;

public class TestRemoteCommands {
    private SmartHome smartHome;
    @BeforeEach
    void setUpSmartHome() {
        ApplicationContext context = new AnnotationConfigApplicationContext(SmartHomeConfiguration.class);
        smartHome = context.getBean(SmartHome.class);
    }

    void turnOnSomeLights() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        for (int i = 0; i < rooms.size(); i++) {
            if (i % 2 == 0) continue;
            List<Light> lights = new ArrayList<>(rooms.get(i).getLights());
            for (int j = 0; j < lights.size(); j++) {
                if (j % 2 == 0) continue;
                lights.get(j).turnOn();
            }
        }
    }

    void turnOffSomeLights() {
        List<Room> rooms = new ArrayList<>(smartHome.getRooms());
        for (int i = 0; i < rooms.size(); i++) {
            if (i % 2 == 0) continue;
            List<Light> lights = new ArrayList<>(rooms.get(i).getLights());
            for (int j = 0; j < lights.size(); j++) {
                if (j % 2 == 0) continue;
                lights.get(j).turnOff();
            }
        }
    }

    @Test
    void checkOffAllLightsInHome_whenExecutedTurnOffAllLightsCommand() {
        turnOnSomeLights();
        Command command = new TurnOffAllLightsCommand(smartHome);
        command.execute();
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                assertEquals(false, light.isOn());
            }
        }
    }

    @Test
    void checkOnAllLightsInHome_whenExecutedTurnOnAllLightsCommand() {
        turnOffSomeLights();
        Command command = new TurnOnAllLightsCommand(smartHome);
        command.execute();
        for (Room room : smartHome.getRooms()) {
            for (Light light : room.getLights()) {
                assertEquals(true, light.isOn());
            }
        }
    }

    @Test
    void checkOnLightInHall_whenExecutedTurnOnLightInHallCommand() {
        Room hall = null;
        for (Room room : smartHome.getRooms()) {
            if (room.getName().equals("hall")) hall = room;
        }
        for (Light light : hall.getLights()) {
            light.turnOff();
        }
        Command command = new TurnOnLightInHallCommand(smartHome);
        command.execute();
        for (Light light : hall.getLights()) {
            assertEquals(true, light.isOn());
        }
    }

    @Test
    void checkClosedDoorInHall_whenExecutedCloseFrontDoorCommand() {
        Room hall = null;
        for (Room room : smartHome.getRooms()) {
            if (room.getName().equals("hall")) hall = room;
        }
        for (Door door : hall.getDoors()) {
            door.open();
        }
        Command command = new CloseFrontDoorCommand(smartHome);
        command.execute();
        for (Door door : hall.getDoors()) {
            assertEquals(false, door.isOpen());
        }
    }

    @Test
    void checkSignalizationIsActivated_whenExecutedActivateSignalizationCommand() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        Command command = new ActivateSignalizationCommand(signalization, signalizationCode);
        command.execute();
        assertEquals(true, signalization.isActivated());
    }

    @Test
    void checkSignalizationIsAlarmed_whenExecutedAlarmSignalizationCommand() {
        Signalization signalization = new Signalization();
        int signalizationCode = 123;
        signalization.activate(signalizationCode);
        Command command = new AlarmSignalizationCommand(signalization);
        command.execute();
        assertEquals(true, signalization.isAlarm());
    }
}
