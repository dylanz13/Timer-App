package model;

import org.junit.jupiter.api.Test;
import persistence.JsonReader;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;

public class JsonReaderTest {

    @Test
    void testReaderNonExistentFile() {
        JsonReader reader = new JsonReader("./data/noSuchFile.json");
        try {
            HashMap<String, Object> objects = reader.read();
            fail("IOException expected");
        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testReaderEmptyEverything() {
        JsonReader reader = new JsonReader("./data/testReaderEmptyEverything.json");
        try {
            HashMap<String, Object> objects = reader.read();
            assertTrue(((ArrayList<Subject>) objects.get("incSub")).isEmpty());
            assertTrue(((ArrayList<Subject>) objects.get("comSub")).isEmpty());
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }

    @Test
    void testReaderGeneralEverything() {
        JsonReader reader = new JsonReader("./data/testReaderGeneralEverything.json");
        try {
            HashMap<String, Object> objects = reader.read();
            assertEquals("Math 100", ((ArrayList<Subject>) objects.get("incSub")).get(0).getDescription());
            assertEquals(113, ((ArrayList<Subject>) objects.get("incSub")).get(0).getSecondsRemaining());
            assertEquals("Is",
                    ((ArrayList<Subject>) objects.get("incSub")).get(0).getDetails().get(0).getDescription());
            assertEquals("Life",
                    ((ArrayList<Subject>) objects.get("incSub")).get(0).getDetails().get(1).getDescription());


            assertEquals(8, objects.get("secs"));
            assertTrue((Boolean) objects.get("run?"));
        } catch (IOException e) {
            fail("Couldn't read from file");
        }
    }
}
