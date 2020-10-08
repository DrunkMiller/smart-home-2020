package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.*;
import java.io.IOException;


import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class Application {
    public static void main(String... args) throws IOException {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        SmartHome smartHome = reader.read();

        EventsManager eventsManager = new SimpleEventsManager(smartHome);
        eventsManager.addHandler(new LightEventHandler());
        eventsManager.addHandler(new DoorEventHandler());
        eventsManager.addHandler(new HallDoorEventHandler());

        EventsReceiver eventsReceiver = new RandomEventsReceiver();

        SmartHomeDispatcher smartHomeDispatcher = new SmartHomeDispatcher(
                smartHome,
                eventsManager,
                eventsReceiver
        );
        smartHomeDispatcher.start();
    }


}
