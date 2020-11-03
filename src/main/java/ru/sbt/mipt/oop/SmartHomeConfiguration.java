package ru.sbt.mipt.oop;

import com.coolcompany.smarthome.events.SensorEventsManager;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Qualifier;
import org.springframework.context.annotation.Bean;
import org.springframework.context.annotation.Configuration;
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

import java.util.Collection;
import java.util.HashMap;
import java.util.Map;

@Configuration
public class SmartHomeConfiguration {

    @Bean
    SmartHome smartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        return reader.read();
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

    @Bean
    EventTypeMapper eventTypeMapper() {
        Map<String, SensorEventType> map = new HashMap<>();
        map.put("LightIsOn", SensorEventType.LIGHT_ON);
        map.put("LightIsOff", SensorEventType.LIGHT_OFF);
        map.put("DoorIsOpen", SensorEventType.DOOR_OPEN);
        map.put("DoorIsClosed", SensorEventType.DOOR_CLOSED);
        map.put("DoorIsLocked", SensorEventType.DOOR_CLOSED);
        map.put("DoorIsUnlocked", SensorEventType.DOOR_OPEN);
        return new EventTypeMapper(map);
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
}
