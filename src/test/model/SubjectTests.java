package model;

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
        assertTrue(s.getDetails().isEmpty());
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
    void testCountDown() {
        s.countDown();
        assertEquals(119, s.getSecondsRemaining());
        for (int i = 0; i < 110; i++) {
            s.countDown();
        }
        assertEquals(9, s.getSecondsRemaining());
    }

    //TODO: add a toString() method test once implementation is finished
}