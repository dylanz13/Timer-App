package model;

import java.util.ArrayList;
import java.util.Iterator;

/*
Represents a list of subjects that are either completed, or incomplete.
*/

public class SubjectManager {
    private ArrayList<Subject> subjects;
    private ArrayList<Subject> completedSubjects;

    public SubjectManager() {
        subjects = new ArrayList<>();
        completedSubjects = new ArrayList<>();
    }

    public SubjectManager(ArrayList<Subject> subjects, ArrayList<Subject> completedSubjects) {
        this.subjects = subjects;
        this.completedSubjects = completedSubjects;
    }

    public void addSubject(Subject subject) {
        if (!subjects.contains(subject)) {
            EventLog.getInstance().logEvent(new Event("Added Subject: " + subject.getDescription()));
            subjects.add(subject);
        }
    }

    //Modifies: this
    //Effects: removes subject with given description from both completed and incomplete subject Lists.
    public void removeSubject(String s) {
        tryRemoveSubject(subjects, s);
        tryRemoveSubject(completedSubjects, s);
    }

    //Helper to remove subject with given description
    private void tryRemoveSubject(ArrayList<Subject> s, String desc) {
        Iterator<Subject> iter = s.iterator();

        while (iter.hasNext()) {
            Subject subject = iter.next();

            if (subject.getDescription().equals(desc)) {
                EventLog.getInstance().logEvent(new Event("Removed Subject: " + subject.getDescription()));
                iter.remove();
            }
        }
    }

    //modifies: model.EventLog
    //effects: stores current subject edit in the EventLog
    public void updateSubjectLog(String s) {
        EventLog.getInstance().logEvent(new Event(s));
    }

    public Subject getSubject(String s) {
        Subject tempSubject = null;
        for (Subject subject : subjects) {
            if (subject.getDescription().equals(s)) {
                tempSubject = subject;
            }
        }
        return tempSubject;
    }

    public ArrayList<Subject> getIncSubjects() {
        return subjects;
    }

    public ArrayList<Subject> getComSubjects() {
        return completedSubjects;
    }
}
