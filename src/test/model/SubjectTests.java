package model;

import org.json.JSONArray;
import org.json.JSONObject;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

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
        assertTrue(s.getDetails().isEmpty());
    }

    @Test
    void testSecondConstructor() {
        s = new Subject("Math", new ArrayList<>(), 120);
        assertEquals("Math", s.getDescription());
        assertEquals(120, s.getSecondsRemaining());
        assertEquals(0,s.getDetails().size());
    }

    @Test
    void testConstructorNegSeconds() {
        s = new Subject("Math 100", -25);
        assertEquals("Math 100", s.getDescription());
        assertEquals(0, s.getSecondsRemaining());
        assertTrue(s.getDetails().isEmpty());
    }

    @Test
    void testSetDescription() {
        s.setDescription("English");
        assertEquals("English", s.getDescription());
    }

    @Test
    void testAddDetail() {
        s.addDetails(new Detail("Finish Group Project"));
        assertEquals(1, s.getDetails().size());
        assertEquals("Finish Group Project", s.getDetails().get(0).getDescription());
        s.addDetails(new Detail("Before Friday, March 3rd"));
        s.addDetails(new Detail("Be sure to set up a day for every group member to meet up"));
        assertEquals(3, s.getDetails().size());
        assertEquals("Before Friday, March 3rd", s.getDetails().get(1).getDescription());
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

    @Test
    void testToJsonWithDetails() {
        s.addDetails(new Detail("Finish Group Project"));
        s.addDetails(new Detail("Before Friday, March 3rd"));
        s.addDetails(new Detail("Be sure to set up a day for every group member to meet up"));

        JSONObject jsonObject = s.toJson();
        assertEquals("Math", jsonObject.getString("name"));
        assertEquals(120, jsonObject.getInt("time left"));
        JSONArray jsonArray = jsonObject.getJSONArray("details");
        ArrayList<Detail> details = toDetails(jsonArray);
        assertEquals(3, jsonArray.length());
        assertEquals("Finish Group Project", details.get(0).getDescription());
        assertEquals("Be sure to set up a day for every group member to meet up",
                details.get(2).getDescription());
    }

    private ArrayList<Detail> toDetails(JSONArray jsonArray) {
        ArrayList<Detail> tempDetailsArray = new ArrayList<>();
        if (jsonArray != null) {
            for (int i = 0; i < jsonArray.length(); i++) {
                tempDetailsArray.add(new Detail(jsonArray.getJSONObject(i).getString("description")));
            }
        }
        return tempDetailsArray;
    }

    //TODO: add a toString() method test once implementation is finished
}