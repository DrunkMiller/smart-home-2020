package ru.sbt.mipt.oop;

import com.google.gson.Gson;
import ru.sbt.mipt.oop.devices.SmartHome;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

public class SmartHomeFromJsonFileReader implements SmartHomeReader {
    private final String file;

    public SmartHomeFromJsonFileReader(String file) {
        this.file = file;
    }

    public SmartHome read() {
        try {
            Gson gson = new Gson();
            String json = new String(Files.readAllBytes(Paths.get(file)));
            return gson.fromJson(json, SmartHome.class);
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }
}
