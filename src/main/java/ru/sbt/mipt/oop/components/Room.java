package ru.sbt.mipt.oop.components;

import ru.sbt.mipt.oop.actions.Action;

import java.util.Collection;
import java.util.List;

public class Room implements HomeComponent {
    private Collection<HomeComponent> lights;
    private Collection<HomeComponent> doors;
    private String name;

    public Room(Collection<HomeComponent> lights, Collection<HomeComponent> doors, String name) {
        this.lights = lights;
        this.doors = doors;
        this.name = name;
    }

    public Collection<HomeComponent> getLights() {
        return lights;
    }

    public Collection<HomeComponent> getDoors() {
        return doors;
    }

    public String getName() {
        return name;
    }

    @Override
    public void execute(Action action) {
        action.accept(this);
        lights.forEach(light -> light.execute(action));
        doors.forEach(door -> door.execute(action));
    }

    @Override
    public String getId() {
        return name;
    }
}
