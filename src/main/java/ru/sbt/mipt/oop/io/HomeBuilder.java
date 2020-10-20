package ru.sbt.mipt.oop.io;

import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.components.decorators.HomeComponentWithSignalization;
import ru.sbt.mipt.oop.io.json.HomeComponentAdapter;

import java.io.BufferedWriter;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.nio.file.Paths;
import java.util.Arrays;

public class HomeBuilder {

    public static void main(String[] args) throws IOException {
        Room kitchen = new Room(Arrays.asList(new Light("1", false), new Light("2", true)),
                Arrays.asList(new Door(false, "1")),
                "kitchen");
        Room bathroom = new Room(Arrays.asList(new Light("3", true)),
                Arrays.asList(new Door(false, "2")),
                "bathroom");
        Room bedroom = new Room(Arrays.asList(new Light("4", false), new Light("5", false), new Light("6", false)),
                Arrays.asList(new Door(true, "3")),
                "bedroom");
        Room hall = new Room(Arrays.asList(
                new Light("7", false),
                new Light("8", false),
                new Light("9", false)),
                Arrays.asList(new Door(false, "4")),
                "hall");
        HomeComponent smartHomeWithSignalization = new HomeComponentWithSignalization(
                "1",
                new SmartHome(Arrays.asList(kitchen, bathroom, bedroom, hall))
        );
        Gson gson = new GsonBuilder()
                .setPrettyPrinting()
                .registerTypeAdapter(HomeComponent.class, new HomeComponentAdapter())
                .create();
        String jsonString = gson.toJson(smartHomeWithSignalization, HomeComponent.class);
        System.out.println(jsonString);
        Path path = Paths.get("smart-home-2.js");
        try (BufferedWriter writer = Files.newBufferedWriter(path)) {
            writer.write(jsonString);
        }
    }

}
