package ru.sbt.mipt.oop.events.handlers;

import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.decorators.HomeComponentWithSignalization;
import ru.sbt.mipt.oop.events.SensorEvent;

import static ru.sbt.mipt.oop.events.SensorEventType.*;

public class SignalizationEventHandler implements EventHandler {
    @Override
    public void handle(HomeComponent homeComponent, SensorEvent event) {
        if (event.getType() == SIGNALIZATION_ACTIVATE || event.getType() == SIGNALIZATION_DEACTIVATE) {
            int code = (int) event.getEventArgs();
            homeComponent.execute(component -> {
                if (component instanceof HomeComponentWithSignalization) {
                    HomeComponentWithSignalization homeComponentWithSignalization = (HomeComponentWithSignalization) component;
                    if (homeComponentWithSignalization.getId().equals(event.getObjectId())) {
                        if (event.getType() == SIGNALIZATION_ACTIVATE) {
                            homeComponentWithSignalization.getSignalization().activate(code);
                        }
                        else if (event.getType() == SIGNALIZATION_DEACTIVATE) {
                            homeComponentWithSignalization.getSignalization().deactivate(code);
                        }
                    }
                }
            });
        }
    }
}
