package ru.sbt.mipt.oop.components.decorators;

import ru.sbt.mipt.oop.actions.Action;
import ru.sbt.mipt.oop.components.HomeComponent;

public abstract class HomeComponentDecorator implements HomeComponent {
    protected final HomeComponent component;
    private final String id;

    public HomeComponentDecorator(String id, HomeComponent component) {
        this.component = component;
        this.id = id;
    }

    public HomeComponent getDecoratedComponent() {
        return component;
    }

    public HomeComponent getBaseComponent() {
        HomeComponent tmp = component;
        while (tmp instanceof HomeComponentDecorator) {
            HomeComponentDecorator decorator = (HomeComponentDecorator) tmp;
            tmp = decorator.getDecoratedComponent();
        }
        return tmp;
    }

    @Override
    public void execute(Action action) {
        component.execute(action);
    }

    @Override
    public String getId() {
        return component.getId();
    }
}
