package ru.sbt.mipt.oop;

import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.events.handlers.DoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.HallDoorEventHandler;
import ru.sbt.mipt.oop.events.handlers.LightEventHandler;
import ru.sbt.mipt.oop.events.handlers.SignalizationEventHandler;
import ru.sbt.mipt.oop.events.managers.CompositeEventsManager;
import ru.sbt.mipt.oop.events.managers.EventsManager;
import ru.sbt.mipt.oop.events.receivers.EventsReceiver;
import ru.sbt.mipt.oop.events.receivers.RandomEventsReceiver;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;

import java.io.IOException;

public class Application {
    public static void main(String... args) throws IOException {

        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home.js");
        HomeComponent smartHome = reader.read();

        EventsManager eventsManager = new CompositeEventsManager(smartHome);
        eventsManager.addHandler(new LightEventHandler());
        eventsManager.addHandler(new DoorEventHandler());
        eventsManager.addHandler(new HallDoorEventHandler());
        eventsManager.addHandler(new SignalizationEventHandler());

        EventsReceiver eventsReceiver = new RandomEventsReceiver();

        SmartHomeDispatcher smartHomeDispatcher = new SmartHomeDispatcher(
                smartHome,
                eventsManager,
                eventsReceiver
        );
        smartHomeDispatcher.start();
    }


}
