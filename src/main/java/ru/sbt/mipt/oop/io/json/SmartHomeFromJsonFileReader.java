package ru.sbt.mipt.oop.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.io.SmartHomeReader;

import java.lang.reflect.Type;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeFromJsonFileReader implements SmartHomeReader {
    private final String file;

    public SmartHomeFromJsonFileReader(String file) {
        this.file = file;
    }

    public HomeComponent read() {
        try {
            String json = new String(Files.readAllBytes(Paths.get(file)));
            Gson gson = new GsonBuilder()
                    .registerTypeAdapter(HomeComponent.class, new HomeComponentAdapter())
                    .create();
            return gson.fromJson(json, HomeComponent.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
