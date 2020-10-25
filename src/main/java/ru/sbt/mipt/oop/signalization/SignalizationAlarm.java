package ru.sbt.mipt.oop.signalization;

public class SignalizationAlarm implements SignalizationState {
    private Signalization signalization;
    private final int code;

    public SignalizationAlarm(Signalization signalization, int code) {
        this.signalization = signalization;
        this.code = code;
    }

    @Override
    public void setActivate(int code) {
        System.out.println("Signalization in alarm state");
    }

    @Override
    public void setDeactivate(int code) {
        if (this.code == code) {
            signalization.changeSignalizationState(
                    new SignalizationDeactivated(signalization)
            );
        }
    }

    @Override
    public void setAlarm() {
        System.out.println("Signalization is already alarm state");
    }

    @Override
    public String toString() {
        return "alarm   ";
    }

}
