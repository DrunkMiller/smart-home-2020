package ru.sbt.mipt.oop.remote.commands;

import ru.sbt.mipt.oop.remote.Command;
import ru.sbt.mipt.oop.signalization.Signalization;

public class AlarmSignalizationCommand implements Command {
    private final Signalization signalization;

    public AlarmSignalizationCommand(Signalization signalization) {
        this.signalization = signalization;
    }

    @Override
    public void execute() {
        signalization.alarm();
    }
}
