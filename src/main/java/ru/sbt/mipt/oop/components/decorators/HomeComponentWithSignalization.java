package ru.sbt.mipt.oop.components.decorators;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.signalization.*;

public class HomeComponentWithSignalization extends HomeComponentDecorator {
    private final Signalization signalization;

    public HomeComponentWithSignalization(String id, HomeComponent component) {
        super(id, component);
        signalization = new Signalization();
    }

    public Signalization getSignalization() {
        return signalization;
    }

    @Override
    public void execute(Action action) {
        if (signalization.getSignalizationState() instanceof SignalizationDeactivated) {
            super.execute(action);
        }
        else if (signalization.getSignalizationState() instanceof SignalizationActivated) {
            signalization.getSignalizationState().setAlarm();
        }
        action.accept(this);
    }
}
