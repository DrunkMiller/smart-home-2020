package ru.sbt.mipt.oop.remote.commands;

import ru.sbt.mipt.oop.devices.Door;
import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.Room;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.remote.Command;

public class CloseFrontDoorCommand implements Command {
    private final SmartHome smartHome;

    public CloseFrontDoorCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(room -> {
            if (room instanceof Room && ((Room) room).getName().equals("hall")) {
                ((Room) room).execute(door -> {
                    if (door instanceof Door ) ((Door) door).close();
                });
            }
        });
    }
}
