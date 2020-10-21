package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.events.*;
import ru.sbt.mipt.oop.events.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.managers.CompositeEventsManager;

import java.io.IOException;

public class Application {
    public static void main(String... args) throws IOException {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home-1.js");
        SmartHome smartHome = reader.read();

        EventsManager eventsManager = new CompositeEventsManager(smartHome);
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
