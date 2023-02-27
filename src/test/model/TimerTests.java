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
    public void testStartNoSubjects() {
        t.start(new ArrayList<>(), new ArrayList<>());
        assertTrue(t.isStopped());
    }

    @Test
    public void testStartCompleteSubjects() {
        ArrayList<Subject> s = new ArrayList<>();
        ArrayList<Subject> cs = new ArrayList<>();

        s.add(new Subject("Math", 10));
        s.add(new Subject("CPSC", 10));

        t.start(s, cs);

        ArrayList<Subject> rcs = new ArrayList<>();

        rcs.add(new Subject("Math", 0));
        rcs.add(new Subject("CPSC", 0));
        assertTrue(s.isEmpty());
        int i = 0;
        for (Subject c : cs) {
            assertEquals(rcs.get(i).getDescription(), c.getDescription());
            assertEquals(rcs.get(i).getSecondsRemaining(), c.getSecondsRemaining());
            i++;
        }
    }

    @Test
    public void testStartIncompleteSubjects() {
        ArrayList<Subject> s = new ArrayList<>();
        ArrayList<Subject> cs = new ArrayList<>();

        s.add(new Subject("Math", 10));
        s.add(new Subject("English", 2));
        s.add(new Subject("CPSC", 10));

        t.start(s, cs);

        ArrayList<Subject> rs = new ArrayList<>();
        ArrayList<Subject> rcs = new ArrayList<>();

        rs.add(new Subject("CPSC", 2));
        rcs.add(new Subject("Math", 0));
        rcs.add(new Subject("English", 0));
        assertEquals(rs.get(0).getDescription(), s.get(0).getDescription());
        assertEquals(rs.get(0).getSecondsRemaining(), s.get(0).getSecondsRemaining());
        int i = 0;
        for (Subject c : cs) {
            assertEquals(rcs.get(i).getDescription(), c.getDescription());
            assertEquals(rcs.get(i).getSecondsRemaining(), c.getSecondsRemaining());
            i++;
        }

    }
}
