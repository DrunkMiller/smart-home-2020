import  static org.junit.jupiter.api.Assertions.*;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ru.sbt.mipt.oop.components.*;
import ru.sbt.mipt.oop.components.decorators.HomeComponentDecorator;
import ru.sbt.mipt.oop.io.json.SmartHomeFromJsonFileReader;
import ru.sbt.mipt.oop.io.SmartHomeReader;
import java.lang.reflect.Field;
import java.util.ArrayList;
import java.util.Collection;

public class TestSmartHome {
    private HomeComponent smartHome;

    private Collection<HomeComponent> getRoomsFromSmartHome(HomeComponent component) {
        try {
            SmartHome smartHome = null;
            if (component instanceof HomeComponentDecorator) {
                smartHome = (SmartHome) ((HomeComponentDecorator) component).getBaseComponent();
            }
            else {
                smartHome = (SmartHome) component;
            }
            Field field = smartHome.getClass().getDeclaredField("rooms");
            field.setAccessible(true);
            return (Collection<HomeComponent>) field.get(smartHome);
        }
        catch (Exception exception) {
            return new ArrayList<>();
        }
    }

    @BeforeEach
    void setUpSmartHome() {
        SmartHomeReader reader = new SmartHomeFromJsonFileReader("smart-home.js");
        smartHome = reader.read();
    }

    @Test
    void iterRooms_checkCorrectPassageOnRooms() {
        Collection<HomeComponent> actualRooms = getRoomsFromSmartHome(smartHome);
        Collection<Room> expectedRooms = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Room) {
                expectedRooms.add((Room) component);
            }
        });
        assertIterableEquals(expectedRooms, actualRooms);
    }

    @Test
    void iterDoors_checkCorrectPassageOnDoors() {
        Collection<HomeComponent> actualDoors = new ArrayList<>();
        for (HomeComponent component : getRoomsFromSmartHome(smartHome)) {
            Room room = (Room) component;
            actualDoors.addAll(room.getDoors());
        }
        Collection<Door> expectedDoors = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Door) {
                expectedDoors.add((Door) component);
            }
        });
        assertIterableEquals(expectedDoors, actualDoors);
    }

    @Test
    void iterLights_checkCorrectPassageOnLights() {
        Collection<HomeComponent> actualLights = new ArrayList<>();
        for (HomeComponent component : getRoomsFromSmartHome(smartHome)) {
            Room room = (Room) component;
            actualLights.addAll(room.getLights());
        }
        Collection<Light> expectedLights = new ArrayList<>();
        smartHome.execute(component -> {
            if (component instanceof Light) {
                expectedLights.add((Light) component);
            }
        });
        assertIterableEquals(expectedLights, actualLights);
    }
}
