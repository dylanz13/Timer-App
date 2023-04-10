package persistence;

import model.Subject;
import model.Timer;
import org.junit.jupiter.api.Test;
import persistence.JsonReader;
import persistence.JsonWriter;
import ui.TimerApp;

import java.io.FileNotFoundException;
import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;

import static org.junit.jupiter.api.Assertions.*;
import static org.junit.jupiter.api.Assertions.assertTrue;

public class JsonWriterTest {
    @Test
    void testWriterInvalidFile() {
        try {
            JsonWriter writer = new JsonWriter("./data/my\0illegal:fileName.json");
            writer.open();
            fail("IOException was expected");

        } catch (IOException e) {
            // pass
        }
    }

    @Test
    void testWriterEmptyEverything() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyEverything.json");
            writer.open();
            writer.add(new ArrayList<>(), "inc");
            writer.add(new ArrayList<>(), "com");
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyEverything.json");
            try {
                HashMap<String, Object> objects = reader.read();
                assertTrue(((ArrayList<Subject>) objects.get("incSub")).isEmpty());
                assertTrue(((ArrayList<Subject>) objects.get("comSub")).isEmpty());
            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }
    }

    @Test
    void testWriterGeneralEverything() {
        try {
            JsonWriter writer = new JsonWriter("./data/testWriterEmptyEverything.json");
            writer.open();
            ArrayList<Subject> s = new ArrayList<>();
            s.add(new Subject("Math 100", 113, 0));
            s.add(new Subject("Ela 30 IB", 3600));

            writer.add(s, "inc");
            writer.add(new ArrayList<>(), "com");
            writer.add(new Timer(20));
            writer.write();
            writer.close();

            JsonReader reader = new JsonReader("./data/testWriterEmptyEverything.json");
            try {
                HashMap<String, Object> objects = reader.read();
                assertEquals("Math 100", ((ArrayList<Subject>) objects.get("incSub")).get(0).getDescription());
                assertEquals(113, ((ArrayList<Subject>) objects.get("incSub")).get(0).getSecondsRemaining());

                assertEquals(20, objects.get("secs"));
                assertFalse((Boolean) objects.get("run?"));
            } catch (IOException e) {
                fail("Exception should not have been thrown");
            }
        } catch (FileNotFoundException e) {
            fail("Exception should not have been thrown");
        }
    }
}
