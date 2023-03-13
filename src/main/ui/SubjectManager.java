package ui;

import model.Subject;

import java.util.ArrayList;

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
        subjects.add(subject);
    }

    public void removeSubject(String s) {
        subjects.removeIf(subject -> subject.getDescription().equals(s));
        completedSubjects.removeIf(subject -> subject.getDescription().equals(s));
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

    public boolean hasSubject(String s) {
        ArrayList<String> descriptions = new ArrayList<>();
        subjects.forEach(subject -> descriptions.add(subject.getDescription()));
        return (descriptions.contains(s));
    }

    public ArrayList<Subject> getIncSubjects() {
        return subjects;
    }

    public ArrayList<Subject> getComSubjects() {
        return completedSubjects;
    }
}
