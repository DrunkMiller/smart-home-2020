package ru.sbt.mipt.oop.signalization;

public class Signalization {
    private SignalizationState state;

    public Signalization() {
        state = new SignalizationDeactivated(this);
    }

    public SignalizationState getSignalizationState() {
        return state;
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

    void changeSignalizationState(SignalizationState newState) {
        state = newState;
    }
}
