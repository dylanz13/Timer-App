package model;

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
    void testWriterEmptyWorkroom() {
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
}
