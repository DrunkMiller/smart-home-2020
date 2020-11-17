package ru.sbt.mipt.oop.remote.commands;

import ru.sbt.mipt.oop.events.SensorEvent;
import ru.sbt.mipt.oop.events.managers.EventsManagerWithSignalization;
import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.signalization.Signalization;

import static ru.sbt.mipt.oop.events.SensorEventType.SIGNALIZATION_ACTIVATE;

public class ActivateSignalizationCommand implements Command {
    private final Signalization signalization;
    private int signalizationCode;

    public ActivateSignalizationCommand(Signalization signalization, int signalizationCode) {
        this.signalization = signalization;
        this.signalizationCode = signalizationCode;
    }

    @Override
    public void execute() {
        signalization.activate(signalizationCode);
    }
}
