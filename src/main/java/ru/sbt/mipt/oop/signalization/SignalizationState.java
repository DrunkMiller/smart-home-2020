package ru.sbt.mipt.oop.signalization;

public abstract class SignalizationState {
    protected final Signalization signalization;

    public SignalizationState(Signalization signalization) {
        this.signalization = signalization;
    }

    public abstract void setActivate(int code);
    public abstract void setDeactivate(int code);
    public abstract void setAlarm();
}
