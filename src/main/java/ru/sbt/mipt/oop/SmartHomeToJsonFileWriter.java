package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.devices.SmartHome;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;

public class SmartHomeToJsonFileWriter implements SmartHomeWriter{
    private final SmartHome smartHome;

    public SmartHomeToJsonFileWriter(SmartHome smartHome) {
        this.smartHome = smartHome;
    }

    public void write() {
        Gson gson = new GsonBuilder().setPrettyPrinting().create();
        String jsonString = gson.toJson(smartHome);
        System.out.println(jsonString);
        Path path = Paths.get("output.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonString);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
