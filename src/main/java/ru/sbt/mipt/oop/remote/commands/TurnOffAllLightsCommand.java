package ru.sbt.mipt.oop.remote.commands;

import ru.sbt.mipt.oop.devices.Light;
import ru.sbt.mipt.oop.devices.SmartHome;
import ru.sbt.mipt.oop.remote.Command;

public class TurnOffAllLightsCommand implements Command {
    private final SmartHome smartHome;

    public TurnOffAllLightsCommand(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    @Override
    public void execute() {
        smartHome.execute(light -> {
            if (light instanceof Light) ((Light) light).turnOff();
        });
    }
}
