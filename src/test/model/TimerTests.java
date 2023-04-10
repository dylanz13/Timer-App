package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import ui.Draw;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

//A test class that makes sure Timer is implemented correctly //TODO: FIX TESTS
public class TimerTests {
    private Timer t;
    private Draw shape;
    private SubjectManager sm;

    @BeforeEach
    public void setUp() {
        t = new Timer(20);
        shape = new Draw("00:00");
        sm = new SubjectManager();
    }

    @Test
    public void testConstructor() {
        assertEquals(20, t.getRemainingTime());
        assertFalse(t.isRunning());
        assertFalse(t.isPaused());
    }

    @Test
    public void testSetRemainingTime() {
        t.setRemainingTime(40);
        assertEquals(40, t.getRemainingTime());
    }

    @Test
    public void setPaused() {
        t.setPaused(true);
        assertTrue(t.isPaused());
    }

    @Test
    public void testStop() {
        t.stop();
        assertFalse(t.isRunning());
        t.start(sm, shape);
        t.stop();
        assertFalse(t.isRunning());
    }

    @Test
    public void testStartNoSubjects() {
        t.start(sm, shape);
        assertFalse(t.isRunning());
    }

    @Test
    public void testStartCompleteSubjects() {

        sm.addSubject(new Subject("Math", 10));
        sm.addSubject(new Subject("CPSC", 10));

        t.start(sm, shape);

        ArrayList<Subject> rcs = new ArrayList<>();

        rcs.add(new Subject("Math", 0));
        rcs.add(new Subject("CPSC", 0));
        assertTrue(sm.getIncSubjects().isEmpty());
        int i = 0;
        for (Subject c : sm.getComSubjects()) {
            assertEquals(rcs.get(i), c);
            i++;
        }
        assertFalse(t.isRunning());
    }

    @Test
    public void testStartIncompleteSubjects() {

        sm.addSubject(new Subject("Math", 10));
        sm.addSubject(new Subject("English", 2));
        sm.addSubject(new Subject("CPSC", 10));

        t.start(sm, shape);

        ArrayList<Subject> rs = new ArrayList<>();
        ArrayList<Subject> rcs = new ArrayList<>();

        rs.add(new Subject("CPSC", 2));
        rcs.add(new Subject("Math", 0));
        rcs.add(new Subject("English", 0));
        assertEquals(rs.get(0), sm.getIncSubjects().get(0));
        int i = 0;
        for (Subject c : sm.getComSubjects()) {
            assertEquals(rcs.get(i), c);
            i++;
        }
        assertFalse(t.isRunning());
    }
}
