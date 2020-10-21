package ru.sbt.mipt.oop.signalization;

public class SignalizationActivated extends SignalizationState {
    private final int code;

    public SignalizationActivated(Signalization signalization, int code) {
        super(signalization);
        this.code = code;
    }

    @Override
    public void setActivate(int code) {
        System.out.println("Signalization is already activated");
        return;
    }

    @Override
    public void setDeactivate(int code) {
        if (this.code == code) {
            signalization.changeSignalizationState(
                    new SignalizationDeactivated(signalization)
            );
        }
        else {
            setAlarm();
        }
    }

    @Override
    public void setAlarm() {
        signalization.changeSignalizationState(
                new SignalizationAlarm(signalization, code)
        );
    }

    @Override
    public String toString() {
        return "activated";
    }
}
