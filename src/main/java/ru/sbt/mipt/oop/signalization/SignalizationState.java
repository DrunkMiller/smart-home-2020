package ru.sbt.mipt.oop.signalization;

public interface SignalizationState {
    public abstract void setActivate(int code);
    public abstract void setDeactivate(int code);
    public abstract void setAlarm();
}
