package ru.sbt.mipt.oop.events.managers.adapters;

import com.coolcompany.smarthome.events.CCSensorEvent;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.SensorEventType;
import ru.sbt.mipt.oop.events.managers.EventsManager;

public class EventHandlerCCAdapter implements com.coolcompany.smarthome.events.EventHandler {
    EventsManager manager;
    EventTypeMapper mapper;

    public EventHandlerCCAdapter(EventsManager manager, EventTypeMapper mapper) {
        this.manager = manager;
        this.mapper = mapper;
    }

    @Override
    public void handleEvent(CCSensorEvent event) {
        SensorEventType eventType = mapper.map(event.getEventType());
        if (eventType != null) {
            manager.handleEvent(new SensorEvent(eventType, event.getObjectId()));
        }
    }
}
