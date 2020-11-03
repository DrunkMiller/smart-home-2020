package ru.sbt.mipt.oop.events.managers.adapters;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;
import ru.sbt.mipt.oop.events.SensorEventType;
import java.util.Map;

public class EventTypeMapper {
    private final Map<String, SensorEventType> mapper;

    public EventTypeMapper(Map<String, SensorEventType> eventTypeMapper) {
        this.mapper = eventTypeMapper;
    }

    public SensorEventType map(String strType) {
        if (mapper.containsKey(strType)) {
            return mapper.get(strType);
        }
        else {
            return null;
        }
    }
}
