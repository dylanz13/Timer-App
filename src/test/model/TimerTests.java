package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//A test class that makes sure Timer is implemented correctly
public class TimerTests {
    Timer t;

    @BeforeEach
    public void setUp() {
        t = new Timer(20);
    }

    @Test
    public void testConstructor() {
        assertEquals(20, t.getRemainingTime());
    }

    @Test
    public void testSetRemainingTime() {
        t.setRemainingTime(40);
        assertEquals(40, t.getRemainingTime());
    }

    @Test
    public void testStop() {
        t.stop();
        assertFalse(t.isStopped());
    }

    @Test
    public void testStart() {
        t.start(new ArrayList<>(), new ArrayList<>());
        assertTrue(t.isStopped());


    }
}
