package ru.sbt.mipt.oop.remote;

import rc.RemoteControl;

import java.util.Map;

public class SmartHomeRemoteController implements RemoteControl {

    private final Map<String, Command> registeredCommands;

    public SmartHomeRemoteController(Map<String, Command> registerCommands) {
        this.registeredCommands = registerCommands;
    }

    @Override
    public void onButtonPressed(String buttonCode) {
        if (registeredCommands.containsKey(buttonCode)) {
            registeredCommands.get(buttonCode).execute();
        }
    }
}
