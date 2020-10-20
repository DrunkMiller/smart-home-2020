package ru.sbt.mipt.oop.components;

import ru.sbt.mipt.oop.actions.Action;

import java.util.ArrayList;
import java.util.Collection;
import java.util.Collections;
import java.util.List;

public class SmartHome implements HomeComponent {
    Collection<HomeComponent> rooms;

    public SmartHome() {
        rooms = new ArrayList<>();
    }

    public SmartHome(Collection<HomeComponent> rooms) {
        this.rooms = rooms;
    }

//    public void addRoom(HomeComponent room) {
//        if (room instanceof Room) {
//            rooms.add(room);
//        }
//        else {
//            throw new ClassCastException();
//        }
//    }

    @Override
    public void execute(Action action) {
        action.accept(this);
        rooms.forEach(room -> room.execute(action));
    }

    @Override
    public String getId() {
        return "";
    }
}
