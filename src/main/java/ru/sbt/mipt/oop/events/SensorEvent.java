package ru.sbt.mipt.oop.events;

public class SensorEvent {
    private final SensorEventType type;
    private final String objectId;
    private final Object eventArgs;

    public SensorEvent(SensorEventType type, String objectId, Object eventArgs) {
        this.type = type;
        this.objectId = objectId;
        this.eventArgs = eventArgs;
    }

    public SensorEvent(SensorEventType type, String objectId) {
        this(type, objectId, null);
    }

    public SensorEventType getType() {
        return type;
    }

    public String getObjectId() {
        return objectId;
    }

    public Object getEventArgs() {
        return eventArgs;
    }

    @Override
    public String toString() {
        return "SensorEvent{" +
                "type=" + type +
                ", objectId='" + objectId + '\'' +
                '}';
    }
}
