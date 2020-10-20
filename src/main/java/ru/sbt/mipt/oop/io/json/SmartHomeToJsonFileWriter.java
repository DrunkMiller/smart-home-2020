package ru.sbt.mipt.oop.io.json;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.components.HomeComponent;
import ru.sbt.mipt.oop.components.SmartHome;
import ru.sbt.mipt.oop.io.SmartHomeWriter;

import java.io.BufferedWriter;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmartHomeToJsonFileWriter implements SmartHomeWriter {
    private final HomeComponent smartHome;

    public SmartHomeToJsonFileWriter(HomeComponent smartHome) {
        this.smartHome = smartHome;
    }

    public void write() {
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(HomeComponent.class, new HomeComponentAdapter())
                .create();
        String jsonString = gson.toJson(smartHome, HomeComponent.class);
        System.out.println(jsonString);
        Path path = Paths.get("output.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
