package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import static org.junit.jupiter.api.Assertions.*;

//A test class that makes sure Subject is implemented correctly
public class SubjectTests {
    private Subject s;

    @BeforeEach
    public void setUp() {
        s = new Subject("Math", 120);
    }

    @Test
    void testConstructor() {
        assertEquals("Math", s.getDescription());
        assertEquals(120, s.getSecondsRemaining());
        assertEquals(0, s.getSecondsDone());
    }

    @Test
    void testEquals() {
        Subject s1 = new Subject("Math", 120);
        SubjectManager sm = new SubjectManager();
        sm.addSubject(s1);
        sm.addSubject(s);
        assertEquals(s1, s);
        assertEquals(s1, sm.getIncSubjects().get(0));
        assertEquals(1, sm.getIncSubjects().size());

    }

    @Test
    void testSecondConstructor() {
        s = new Subject("Math", 120, 0);
        assertEquals("Math", s.getDescription());
        assertEquals(120, s.getSecondsRemaining());
        assertEquals(0,s.getSecondsDone());
    }

    @Test
    void testConstructorNegSeconds() {
        s = new Subject("Math 100", -25);
        assertEquals("Math 100", s.getDescription());
        assertEquals(0, s.getSecondsRemaining());
        assertEquals(0,s.getSecondsDone());
    }

    @Test
    void testSetDescription() {
        s.setDescription("English");
        assertEquals("English", s.getDescription());
    }

    @Test
    void testAddSeconds() {
        s.addSeconds(300);
        assertEquals(420, s.getSecondsRemaining());
    }

    @Test
    void testSetSeconds() {
        s.setSecondsRemaining(300);
        assertEquals(300, s.getSecondsRemaining());
    }

    @Test
    void testCountDown() {
        s.countDown();
        assertEquals(119, s.getSecondsRemaining());
        for (int i = 0; i < 110; i++) {
            s.countDown();
        }
        assertEquals(9, s.getSecondsRemaining());
    }

    @Test
    void testToJson() {
        JSONObject jsonObject = s.toJson();
        assertEquals("Math", jsonObject.getString("name"));
        assertEquals(120, jsonObject.getInt("time left"));
        JSONArray jsonArray = jsonObject.getJSONArray("details");
        assertEquals(0, jsonArray.length());
    }

    //TODO: add a toString() method test once implementation is finished
}