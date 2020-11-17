package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
import rc.RemoteControlRegistry;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.EventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.events.managers.CompositeEventsManager;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.managers.EventsManagerWithSignalization;
import ru.sbt.mipt.oop.events.managers.adapters.EventHandlerCCAdapter;
import ru.sbt.mipt.oop.events.managers.adapters.EventTypeMapper;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.remote.SmartHomeRemoteController;
import ru.sbt.mipt.oop.remote.commands.*;
import ru.sbt.mipt.oop.signalization.Signalization;

import java.util.*;

@Configuration
public class SmartHomeConfiguration {

    @Bean
    SmartHome smartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        return reader.read();
    }

    @Bean
    Signalization signalization() {
        return new Signalization();
    }

    @Bean
    int signalizationCode() {
        return 123;
    }

    @Bean
    DoorEventHandler doorEventHandler() {return new DoorEventHandler();}
    @Bean
    HallDoorEventHandler hallDoorEventHandler() {return new HallDoorEventHandler();}
    @Bean
    LightEventHandler lightEventHandler() {return new LightEventHandler();}

    @Bean
    EventsManager compositeEventsManager(Collection<EventHandler> handlers) {
        return new CompositeEventsManager(smartHome(), handlers);
    }

    Map<String, SensorEventType> eventsNamesByEventsTypes() {
        Map<String, SensorEventType> map = new HashMap<>();
        map.put("LightIsOn", SensorEventType.LIGHT_ON);
        map.put("LightIsOff", SensorEventType.LIGHT_OFF);
        map.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
        map.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        map.put("DoorIsLocked", SensorEventType.DOOR_CLOSED);
        map.put("DoorIsUnlocked", SensorEventType.DOOR_OPEN);
        return map;
    }

    @Bean
    EventTypeMapper eventTypeMapper() {
        return new EventTypeMapper(eventsNamesByEventsTypes());
    }

    @Bean
    EventHandlerCCAdapter eventHandlerCCAdapter(EventsManager compositeEventsManager, EventTypeMapper eventTypeMapper) {
        return new EventHandlerCCAdapter(compositeEventsManager, eventTypeMapper);
    }

    @Bean
    SensorEventsManager sensorEventsManager(EventHandlerCCAdapter eventHandlerCCAdapter) {
        SensorEventsManager sensorEventsManager = new SensorEventsManager();
        sensorEventsManager.registerEventHandler(eventHandlerCCAdapter);
        return sensorEventsManager;
    }

    @Bean
    ActivateSignalizationCommand activateSignalizationCommand() {return new ActivateSignalizationCommand(signalization(), signalizationCode());}
    @Bean
    AlarmSignalizationCommand alarmSignalizationCommand() {return new AlarmSignalizationCommand(signalization());}
    @Bean
    CloseFrontDoorCommand closeFrontDoorCommand() {return new CloseFrontDoorCommand(smartHome());}
    @Bean
    TurnOffAllLightsCommand turnOffAllLightsCommand() {return new TurnOffAllLightsCommand(smartHome());}
    @Bean
    TurnOnAllLightsCommand turnOnAllLightsCommand() {return new TurnOnAllLightsCommand(smartHome());}
    @Bean
    TurnOnLightInHallCommand turnOnLightInHallCommand() {return new TurnOnLightInHallCommand(smartHome());}

    @Bean
    String remoteControllerId() {
        return "123";
    }

    @Bean
    RemoteControlRegistry remoteControlRegistry() {
        return new RemoteControlRegistry();
    }

    Map<String, Command> remoteButtonsByCommands() {
        Map<String, Command> map = new HashMap<>();
        map.put("A", activateSignalizationCommand());
        map.put("B", alarmSignalizationCommand());
        map.put("C", closeFrontDoorCommand());
        map.put("D", turnOffAllLightsCommand());
        map.put("1", turnOnAllLightsCommand());
        map.put("2", turnOnLightInHallCommand());
        return map;
    }

    @Bean
    SmartHomeRemoteController smartHomeRemoteController(Collection<Command> commands) {
        SmartHomeRemoteController remoteController = new SmartHomeRemoteController(remoteButtonsByCommands());
        remoteControlRegistry().registerRemoteControl(
                remoteController,
                remoteControllerId()
        );
        return remoteController;
    }
}
