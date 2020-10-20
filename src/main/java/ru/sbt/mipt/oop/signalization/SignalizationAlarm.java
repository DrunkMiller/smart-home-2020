package ru.sbt.mipt.oop.signalization;

public class SignalizationAlarm extends SignalizationState {
    private final int code;

    public SignalizationAlarm(Signalization signalization, int code) {
        super(signalization);
        this.code = code;
        sendAlarmMessage();
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

    private void sendAlarmMessage() {
        System.out.println("Sending sms");
    }
}
