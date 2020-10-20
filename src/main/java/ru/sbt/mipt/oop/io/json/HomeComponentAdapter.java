package ru.sbt.mipt.oop.io.json;

import com.google.gson.*;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.decorators.HomeComponentDecorator;
import java.lang.reflect.InvocationTargetException;
import java.lang.reflect.Type;

public final class HomeComponentAdapter implements JsonSerializer<HomeComponent>, JsonDeserializer<HomeComponent> {
    @Override
    public JsonElement serialize(HomeComponent object, Type interfaceType, JsonSerializationContext context) {
        JsonObject wrapper = new JsonObject();
        if (object instanceof HomeComponentDecorator) {
            wrapper.add("decorators", getDecorators(object));
        }
        wrapper.addProperty("type", getBaseHomeComponent(object).getClass().getName());
        wrapper.add("data", context.serialize(getBaseHomeComponent(object)));
        return wrapper;
    }

    @Override
    public HomeComponent deserialize(JsonElement elem, Type interfaceType, JsonDeserializationContext context) throws JsonParseException {
        final JsonObject wrapper = (JsonObject) elem;
        final JsonArray decorators = (JsonArray) get(wrapper, "decorators");
        final JsonElement typeName = get(wrapper, "type");
        final JsonElement data = get(wrapper, "data");
        final Type actualType = typeForName(typeName);
        HomeComponent base = context.deserialize(data, actualType);
        if (decorators != null) {
            try {
                HomeComponentDecorator wrapped = null;
                for (int i = 0; i < decorators.size(); i++) {
                    JsonElement typeD = get((JsonObject) decorators.get(i), "type");
                    JsonElement idD = get((JsonObject) decorators.get(i), "id");
                    if (i == 0) wrapped = createWrapper(typeD.getAsString(), idD.getAsString(), base);
                    else wrapped = createWrapper(typeD.getAsString(), idD.getAsString(), wrapped);
                }
                return wrapped;
            } catch (Exception e) {
                System.out.println(e.getMessage());
                e.printStackTrace();
            }
        }
        return context.deserialize(data, actualType);
    }

    private Type typeForName(JsonElement typeElem) {
        try {
            return Class.forName(typeElem.getAsString());
        } catch (ClassNotFoundException e) {
            throw new JsonParseException(e);
        }
    }

    private JsonElement get(final JsonObject wrapper, String memberName) {
        final JsonElement elem = wrapper.get(memberName);
        return elem;
    }

    private JsonArray getDecorators(Object object){
        Object tmp = object;
        JsonArray decorators = new JsonArray();
        while (tmp instanceof HomeComponentDecorator) {
            HomeComponentDecorator decorator = (HomeComponentDecorator) tmp;
            JsonObject decoratorObject = new JsonObject();
            decoratorObject.addProperty("type", decorator.getClass().getName());
            decoratorObject.addProperty("id", decorator.getId());
            decorators.add(decoratorObject);
            tmp = decorator.getDecoratedComponent();
        }
        return decorators;
    }

    private Object getBaseHomeComponent(Object object){
        while (object instanceof HomeComponentDecorator) {
            HomeComponentDecorator decorator = (HomeComponentDecorator) object;
            object = decorator.getDecoratedComponent();
        }
        return object;
    }

    private HomeComponentDecorator createWrapper(String decoratorTypeName, String id, HomeComponent base) throws ClassNotFoundException, NoSuchMethodException, IllegalAccessException, InvocationTargetException, InstantiationException {
            return (HomeComponentDecorator) Class.forName(decoratorTypeName)
                    .getConstructor(String.class, HomeComponent.class)
                    .newInstance(id, base);
    }

}
