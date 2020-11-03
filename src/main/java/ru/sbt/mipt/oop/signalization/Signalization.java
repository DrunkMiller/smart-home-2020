package ru.sbt.mipt.oop.signalization;

public class Signalization {
    private SignalizationState state;

    public Signalization() {
        state = new SignalizationDeactivated(this);
    }

    public void activate(int code) {
        state.setActivate(code);
    }
    public void deactivate(int code) {
        state.setDeactivate(code);
    }
    public void alarm() {
        state.setAlarm();
    }

    public boolean isActivated() {
        return state instanceof SignalizationActivated ? true : false;
    }

    public boolean isDeactivated() {
        return state instanceof SignalizationDeactivated ? true : false;
    }

    public boolean isAlarm() {
        return state instanceof SignalizationAlarm ? true : false;
    }

    void changeSignalizationState(SignalizationState newState) {
        state = newState;
    }
}
