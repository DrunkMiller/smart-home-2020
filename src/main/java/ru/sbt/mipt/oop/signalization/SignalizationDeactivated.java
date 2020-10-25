package ru.sbt.mipt.oop.signalization;

public class SignalizationDeactivated implements SignalizationState {
    private Signalization signalization;

    public SignalizationDeactivated(Signalization signalization) {
        this.signalization = signalization;
    }

    @Override
    public void setActivate(int code) {
        signalization.changeSignalizationState(
                new SignalizationActivated(signalization, code)
        );
    }

    @Override
    public void setDeactivate(int code) {
        System.out.println("Signalization is already deactivated");
    }

    @Override
    public void setAlarm() {
        System.out.println("Signalization in deactivated state");
    }

    @Override
    public String toString() {
        return "deactivated";
    }
}
