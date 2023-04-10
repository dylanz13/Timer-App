package model;

import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import java.util.ArrayList;

import static org.junit.jupiter.api.Assertions.*;

public class SubjectManagerTests {
    private SubjectManager sm;
    Subject s1, s2, s3;

    @BeforeEach
    void setUp() {
        sm = new SubjectManager();
        s1 = new Subject("Math", 200);
        s2 = new Subject("History", 100);
        s3 = new Subject("Linguistics", 350);
    }

    @Test
    void testConstructor() {
        assertTrue(sm.getIncSubjects().isEmpty());
        assertTrue(sm.getComSubjects().isEmpty());

        ArrayList<Subject> incSubs = new ArrayList<>();
        ArrayList<Subject> comSubs = new ArrayList<>();
        incSubs.add(s1);
        incSubs.add(s2);
        comSubs.add(s3);
        sm = new SubjectManager(incSubs, comSubs);
        assertEquals(2, sm.getIncSubjects().size());
        assertEquals(s1, sm.getIncSubjects().get(0));
        assertEquals(s2, sm.getIncSubjects().get(1));
        assertEquals(1, sm.getComSubjects().size());
        assertEquals(s3, sm.getComSubjects().get(0));
    }

    @Test
    void testAddSubject() {
        sm.addSubject(s1);
        sm.addSubject(s1);
        assertEquals(1, sm.getIncSubjects().size());
        assertEquals(0, sm.getComSubjects().size());
        sm.addSubject(s2);
        assertEquals(s1, sm.getIncSubjects().get(0));
        assertEquals(s2, sm.getIncSubjects().get(1));
    }

    @Test
    void testRemoveSubject() {
        sm.addSubject(s1);
        sm.addSubject(s2);

        sm.removeSubject(s2.getDescription());
        assertEquals(1, sm.getIncSubjects().size());
        assertEquals(s1, sm.getIncSubjects().get(0));
    }

    @Test
    void testGetSubject() {
        sm.addSubject(s1);
        sm.addSubject(s2);
        sm.addSubject(s3);

        assertEquals(s1, sm.getSubject(s1.getDescription()));
        assertEquals(s2, sm.getSubject(s2.getDescription()));
        assertEquals(s3, sm.getSubject(s3.getDescription()));
    }

}
